package com.ourkitchen.yourhealth.controller;


import com.ourkitchen.yourhealth.dto.PayUPaymentResponseDTO;
import com.ourkitchen.yourhealth.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<PayUPaymentResponseDTO> proccessPayment(@PathVariable String orderId) {
        PayUPaymentResponseDTO responseDTO = paymentService.processPayment(orderId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<String> refreshPaymentStatus(@PathVariable String orderId) throws IOException {
        String status = paymentService.refreshPaymentStatus(orderId);
        return ResponseEntity.ok(status);
    }



}
