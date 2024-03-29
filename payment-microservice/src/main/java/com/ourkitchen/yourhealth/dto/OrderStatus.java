package com.ourkitchen.yourhealth.dto;

public enum OrderStatus {
    CREATED_NOT_CONFIRMED("Created"),
    WAITING_FOR_PAYMENT("Waiting for payment"),
    PAYMENT_PENDING("Payment pending"),
    PAYMENT_FINISHED_IN_REALISATION("Payment finished, order in realisation"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
