package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.model.Order;
import com.ourkitchen.yourhealth.model.OrderOneDay;
import com.ourkitchen.yourhealth.repository.OrderOneDayRepository;
import com.ourkitchen.yourhealth.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderOneDayRepository orderOneDayRepository;

    @Transactional
    public Order createOrder(Order order) {
        List<OrderOneDay> orderOneDayList = new ArrayList<>();
        long days = 0;

        for(OrderOneDay day : order.getOrderOneDays()) {
            OrderOneDay orderOneDay = OrderOneDay.builder()
                    .mealsIds(day.getMealsIds())
                    .additionalProductsIds(day.getAdditionalProductsIds())
                    .realizationDate(order.getStartDate().plusDays(days).toLocalDate())
                    .build();
            orderOneDayRepository.save(day);
            orderOneDayList.add(orderOneDay);
            days++;
        };

        order.setOrderOneDays(orderOneDayList);
        order.setDaysNumber(days);

        Order savedOrder = orderRepository.save(order);

        log.info(String.format("Placing an order from user %s", order.getUserId()));

        return savedOrder;
    }
}
