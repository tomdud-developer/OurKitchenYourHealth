package com.ourkitchen.yourhealth.publisher;

import com.ourkitchen.yourhealth.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagePublisher {
    private final KafkaTemplate<String, ChangeOrderStatusMessage> kafkaTemplate;

    @Value("${spring.kafka.custom.change-order-status-topic-name}")
    String changeOrderStatusTopicName;

    public void publishChangePaymentStatusMessage(String orderId,
                                                  OrderStatus oldStatus,
                                                  OrderStatus newStatus,
                                                  String clientId) {

        ChangeOrderStatusMessage changePaymentStatusMessage
                = ChangeOrderStatusMessage.builder()
                                            .clientId(clientId)
                                            .orderId(orderId)
                                            .oldStatus(oldStatus)
                                            .newStatus(newStatus)
                                            .build();

        log.info("Publishing info about change order {} status from {} to {}", orderId, oldStatus.toString(), newStatus.toString());

        kafkaTemplate.send(changeOrderStatusTopicName, changePaymentStatusMessage);
    }


}
