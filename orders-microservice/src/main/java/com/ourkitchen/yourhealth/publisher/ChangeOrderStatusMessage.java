package com.ourkitchen.yourhealth.publisher;

import com.ourkitchen.yourhealth.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChangeOrderStatusMessage {
    String orderId;
    String clientId;
    OrderStatus oldStatus;
    OrderStatus newStatus;
}
