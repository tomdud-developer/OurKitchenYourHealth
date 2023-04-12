package com.ourkitchen.yourhealth.publisher;

import com.ourkitchen.yourhealth.model.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagePublisher {
    private final KafkaTemplate<String, ChangePaymentStatusMessage> kafkaTemplate;

    @Value("${spring.kafka.custom.change-payment-status-topic-name}")
    String changePaymentStatusTopicName;

    public void publishChangePaymentStatusMessage(String orderId,
                                                  PaymentStatus oldStatus,
                                                  PaymentStatus newStatus) {

        ChangePaymentStatusMessage changePaymentStatusMessage
                = ChangePaymentStatusMessage.builder()
                                            .orderId(orderId)
                                            .oldStatus(oldStatus)
                                            .newStatus(newStatus)
                                            .build();

        kafkaTemplate.send(changePaymentStatusTopicName, changePaymentStatusMessage);
    }
}
