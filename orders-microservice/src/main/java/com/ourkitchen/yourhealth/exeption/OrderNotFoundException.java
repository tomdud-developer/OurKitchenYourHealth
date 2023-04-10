package com.ourkitchen.yourhealth.exeption;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderNumber) {
        super("Order with number " + orderNumber + " not found.");
    }
}
