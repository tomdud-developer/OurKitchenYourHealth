package com.ourkitchen.yourhealth.publisher;

import com.ourkitchen.yourhealth.model.PaymentStatus;
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
