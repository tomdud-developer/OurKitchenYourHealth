package com.ourkitchen.yourhealth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddress {
    private String street;
    private String houseNumber;
    private String flatNumber;
    private String city;
    private String postalCode;
    private String country;
}
