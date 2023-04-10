package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.client.MealsServiceClient;
import com.ourkitchen.yourhealth.exeption.OrderNotFoundException;
import com.ourkitchen.yourhealth.model.Order;
import com.ourkitchen.yourhealth.model.OrderOneDay;
import com.ourkitchen.yourhealth.model.OrderStatus;
import com.ourkitchen.yourhealth.repository.OrderOneDayRepository;
import com.ourkitchen.yourhealth.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderOneDayRepository orderOneDayRepository;

    private final MealsServiceClient mealsServiceClient;

    @Transactional
    public Order createOrder(Order order) throws IllegalStateException {

        //Checking meals ids
        List<String> mealsIds = order.getOrderOneDays().stream().map(OrderOneDay::getMealsIds).flatMap(Collection::stream).toList();
        Flux<Boolean> booleanFlux = mealsServiceClient.areMealsExists(mealsIds);

        try {
            booleanFlux.subscribe(booleanSignal -> {
                if(!booleanSignal) throw new IllegalStateException("Bad meal id!");
            });
        } catch (IllegalStateException exception) {
            throw new IllegalStateException(exception.getMessage());
        }


        //Calculating order totalPrice
        BigDecimal totalPrice = mealsServiceClient.calculateOrderPrice(mealsIds).block();

        List<OrderOneDay> orderOneDayList = new ArrayList<>();
        long days = 0;

        for(OrderOneDay day : order.getOrderOneDays()) {
            OrderOneDay orderOneDay = OrderOneDay.builder()
                    .mealsIds(day.getMealsIds())
                    .additionalProductsIds(day.getAdditionalProductsIds())
                    .realizationDate(order.getStartDate().plusDays(days).toLocalDate())
                    .build();
            OrderOneDay savedOrderOneDay = orderOneDayRepository.save(orderOneDay);
            orderOneDayList.add(savedOrderOneDay);
            days++;
        };

        order.setOrderOneDays(orderOneDayList);
        order.setDaysNumber(days);
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CREATED_NOT_CONFIRMED);

        Order savedOrder = orderRepository.save(order);

        log.info(String.format("Placing an order from user %s", order.getUserId()));

        return savedOrder;
    }

    @Transactional
    public String confirmOrder(String orderId) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        orderOptional.ifPresentOrElse(order -> {
            log.info("Confirm order with id: {}", orderId);
            order.setIsConfirmed(Boolean.TRUE);
            order.setOrderStatus(OrderStatus.WAITING_FOR_PAYMENT);
            orderRepository.save(order);
        }, () -> {
            throw new OrderNotFoundException(orderId);
        });

        return String.format("Order with number %s confirmed, order status is %s", orderId, OrderStatus.WAITING_FOR_PAYMENT);
    }


    @Transactional
    public Order getOrderInfo(String orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if(orderOptional.isEmpty())
            throw new OrderNotFoundException(orderId);

        Order foundOrder = orderOptional.get();

        return foundOrder;
    }
}
