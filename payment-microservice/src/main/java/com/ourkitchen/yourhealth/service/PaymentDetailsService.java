package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.model.PaymentDetails;
import com.ourkitchen.yourhealth.model.PaymentStatus;
import com.ourkitchen.yourhealth.repository.PaymentDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;

    @Transactional
    public PaymentDetails savePaymentDetails(PaymentDetails paymentDetails) {
        paymentDetails.setPaymentStatus(PaymentStatus.PAYMENT_STARTED);
        return paymentDetailsRepository.save(paymentDetails);
    }

    public PaymentDetails getPaymentById(String paymentId) {
        Optional<PaymentDetails> optionalPaymentDetails = paymentDetailsRepository.findById(Long.getLong(paymentId));
        if (optionalPaymentDetails.isPresent()) {
            return optionalPaymentDetails.get();
        } else throw new RuntimeException("Payment with id doesn't exist");
    }

    public PaymentDetails getPaymentByOrderId(String orderId) {
        PaymentDetails optionalPaymentDetails = paymentDetailsRepository.findByOrderId(orderId).get(0);
        if (optionalPaymentDetails != null) {
            return optionalPaymentDetails;
        } else throw new RuntimeException("Payment with orderId " + orderId + " doesn't exist");
    }
}
