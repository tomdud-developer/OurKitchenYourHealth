package com.ourkitchen.yourhealth.controller;

import com.ourkitchen.yourhealth.model.Order;
import com.ourkitchen.yourhealth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeAnOrder(@RequestBody Order order) throws Exception{
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }


}
