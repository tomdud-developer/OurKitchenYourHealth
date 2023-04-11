package com.ourkitchen.yourhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayUPaymentResponseDTO {
    private StatusDTO status;
    private String redirectUri;
    private String orderId;
    private String extOrderId;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class StatusDTO {
    private String statusCode;

}
