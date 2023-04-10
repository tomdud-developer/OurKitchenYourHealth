package com.ourkitchen.yourhealth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ProcessPaymentRequestDTO {

    private String clientId;
    private String orderId;
    private String currencyCode;

}
