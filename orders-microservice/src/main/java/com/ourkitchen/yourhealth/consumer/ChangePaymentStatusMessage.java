package com.ourkitchen.yourhealth.consumer;

import com.ourkitchen.yourhealth.dto.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChangePaymentStatusMessage {
    String orderId;
    PaymentStatus oldStatus;
    PaymentStatus newStatus;
}
