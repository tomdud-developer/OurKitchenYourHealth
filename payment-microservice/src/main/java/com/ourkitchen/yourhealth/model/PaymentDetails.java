package com.ourkitchen.yourhealth.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
    @Id
    private Long id;
    private String currencyCode;
    private String totalAmount;
    private String orderId;
    private String clientId;
    private String email;
    private PaymentStatus paymentStatus;
    private PaymentServiceEnum paymentServiceEnum;
}
