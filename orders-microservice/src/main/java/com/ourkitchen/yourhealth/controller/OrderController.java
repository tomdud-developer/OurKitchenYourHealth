package com.ourkitchen.yourhealth.controller;

import com.ourkitchen.yourhealth.model.Order;
import com.ourkitchen.yourhealth.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeAnOrder(@RequestBody Order order, HttpServletRequest request) throws Exception{
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @PostMapping("confirm/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.confirmOrder(orderId));
    }


}
