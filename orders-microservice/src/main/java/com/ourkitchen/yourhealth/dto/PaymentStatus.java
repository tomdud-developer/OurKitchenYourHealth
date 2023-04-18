package com.ourkitchen.yourhealth.dto;

public enum PaymentStatus {
    PAYMENT_STARTED("Payment started"),
    PAYMENT_SUCCESSFULLY_FINISHED("Payment successfully finished"),
    PAYMENT_CANCELLED("Payment canceled"),
    PAYMENT_REFUNDED("Payment successfully finished, refunded"),
    PAYMENT_ERROR("Internal error");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
