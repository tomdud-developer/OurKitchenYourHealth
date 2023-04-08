package com.ourkitchen.yourhealth.controller;


import com.ourkitchen.yourhealth.dto.ProcessPaymentRequestDTO;
import com.ourkitchen.yourhealth.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> proccessPayment(@RequestBody ProcessPaymentRequestDTO processPaymentRequestDTO) throws MalformedURLException {
        String url = paymentService.processPayment(processPaymentRequestDTO);
        return ResponseEntity.ok(url);
    }



}
