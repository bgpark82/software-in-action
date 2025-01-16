package com.bgpark.digital_wallet;

public abstract class PaymentMethod {
    // TODO: How to choose id
    protected String id;
    protected User user;

    public PaymentMethod(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }
}
