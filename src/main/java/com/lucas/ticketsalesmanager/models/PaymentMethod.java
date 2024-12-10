package com.lucas.ticketsalesmanager.models;

public enum PaymentMethod {
    BANK_SLIP("Bank Slip"),
    CARD("Card"),
    PHYSICAL_MONEY("Physical Money"),
    PIX("Pix"),
    TED("TED");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
