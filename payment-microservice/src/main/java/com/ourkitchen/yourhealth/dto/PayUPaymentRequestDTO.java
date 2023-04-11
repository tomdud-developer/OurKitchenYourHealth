package com.ourkitchen.yourhealth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PayUPaymentRequestDTO extends PaymentRequestDTO {

    private String notifyUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String totalAmount;
    private BuyerDTO buyer;
    private List<ProductDTO> products;

    private String extOrderId;

    private String visibleDescription;


    @Data
    @Builder
    public static class BuyerDTO {
        private String email;
        private String phone;
        private String firstName;
        private String lastName;
        private String language;
    }

    @Data
    public static class ProductDTO {
        private String name;
        private String unitPrice;
        private String quantity;
    }
}
