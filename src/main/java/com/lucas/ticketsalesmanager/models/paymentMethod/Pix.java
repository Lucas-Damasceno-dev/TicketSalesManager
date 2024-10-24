package com.lucas.ticketsalesmanager.models.paymentMethod;

import java.util.Objects;

public class Pix extends Payment{
    // Attributes
    private String accesskey;

    // Constructor
    public Pix(String accesskey) {
        this.accesskey = accesskey;
    }

    // Getters and Setters
    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    // overrides methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pix pix = (Pix) o;
        return Objects.equals(accesskey, pix.accesskey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accesskey);
    }

    @Override
    public String toString() {
        return "Pix{" +
                "accesskey='" + accesskey + '\'' +
                '}';
    }

    // Methods
    public boolean pay(String accesskey) {
        return this.accesskey.equals(accesskey);
    }
}
