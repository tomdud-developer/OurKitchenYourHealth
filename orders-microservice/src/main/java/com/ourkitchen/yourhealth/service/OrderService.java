package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.repository.OrderOneDayRepository;
import com.ourkitchen.yourhealth.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderOneDayRepository orderOneDayRepository;

}
