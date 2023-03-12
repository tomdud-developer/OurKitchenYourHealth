package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.client.MealsServiceClient;
import com.ourkitchen.yourhealth.model.Order;
import com.ourkitchen.yourhealth.model.OrderOneDay;
import com.ourkitchen.yourhealth.repository.OrderOneDayRepository;
import com.ourkitchen.yourhealth.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public Order createOrder(Order order) throws Exception{

        //Checking meals ids
        List<String> mealsIds = order.getOrderOneDays().stream().map(OrderOneDay::getMealsIds).flatMap(Collection::stream).toList();
        Flux<Boolean> booleanFlux = mealsServiceClient.areMealsExists(mealsIds);

        booleanFlux.doOnEach(booleanSignal -> {
            if(!booleanSignal.get())
                throw new RuntimeException("Bad ids");
        });


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

        Order savedOrder = orderRepository.save(order);

        log.info(String.format("Placing an order from user %s", order.getUserId()));

        return savedOrder;
    }
}
