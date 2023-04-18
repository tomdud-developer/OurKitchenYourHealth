package com.ourkitchen.yourhealth.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumer {

    @KafkaListener(
            topics = "${spring.kafka.custom.change-payment-status-topic-name}",
            containerFactory = "changePaymentStatusMessageKafkaListenerContainerFactory")
    public void greetingListener(ChangePaymentStatusMessage changePaymentStatusMessage) {

    }

}
