package com.ourkitchen.yourhealth.client;

import com.ourkitchen.yourhealth.dto.PaymentRequestDTO;

public interface PaymentInterface<T extends PaymentRequestDTO> {
    String processPayment(T paymentRequestDTO, String accessToken);
}
