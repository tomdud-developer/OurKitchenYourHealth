package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.model.PaymentDetails;
import com.ourkitchen.yourhealth.model.PaymentStatus;
import com.ourkitchen.yourhealth.repository.PaymentDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;

    @Transactional
    public PaymentDetails savePaymentDetails(PaymentDetails paymentDetails) {
        paymentDetails.setPaymentStatus(PaymentStatus.PAYMENT_STARTED);
        return paymentDetailsRepository.save(paymentDetails);
    }
}
