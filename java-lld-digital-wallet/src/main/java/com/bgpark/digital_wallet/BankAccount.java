package com.bgpark.digital_wallet;

import java.time.LocalDateTime;

public class BankAccount extends PaymentMethod {

    private String accountNumber;

    public BankAccount(String id, User user, String accountNumber) {
        super(id, user);
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return this.id;
    }
}
