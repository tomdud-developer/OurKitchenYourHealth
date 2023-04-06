package com.ourkitchen.yourhealth.controller;


import com.ourkitchen.yourhealth.dto.ProcessPaymentRequest;
import com.ourkitchen.yourhealth.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> proccessPayment(@RequestBody ProcessPaymentRequest processPaymentRequest) {

        String url = paymentService.processPayment(processPaymentRequest);

        return ResponseEntity.ok(url);
    }



}
