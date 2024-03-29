package com.ourkitchen.yourhealth.consumer;

import com.ourkitchen.yourhealth.dto.PaymentStatus;
import com.ourkitchen.yourhealth.model.Order;
import com.ourkitchen.yourhealth.model.OrderStatus;
import com.ourkitchen.yourhealth.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {
    private final OrderService orderService;

    @KafkaListener(
            topics = "${spring.kafka.custom.change-payment-status-topic-name}",
            containerFactory = "changePaymentStatusMessageKafkaListenerContainerFactory",
            groupId = "changePaymentStatus")
    public void greetingListener(ChangePaymentStatusMessage changePaymentStatusMessage) {

        String orderId = changePaymentStatusMessage.getOrderId();
        Order order = orderService.getOrderInfo(orderId);

        log.info("Receive kafka changePaymentStatus for orderId {}", orderId);

        OrderStatus currentOrderStatus = order.getOrderStatus();
        if(changePaymentStatusMessage.oldStatus.equals(PaymentStatus.PAYMENT_STARTED)
            && changePaymentStatusMessage.newStatus.equals(PaymentStatus.PAYMENT_SUCCESSFULLY_FINISHED)
                && currentOrderStatus.equals(OrderStatus.WAITING_FOR_PAYMENT)
        ) {
            order.setOrderStatus(OrderStatus.PAYMENT_FINISHED_IN_REALISATION);
            orderService.updateOrder(order);
        }
    }

}
