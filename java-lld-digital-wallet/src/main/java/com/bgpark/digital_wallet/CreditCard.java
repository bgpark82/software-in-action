package com.bgpark.digital_wallet;

import java.time.LocalDateTime;

public class CreditCard extends PaymentMethod {

    private LocalDateTime expirationDate;
    private String number;
    private int cvv; // Card Verification Value

    public CreditCard(String id, User user, LocalDateTime expirationDate, String number, int cvv) {
        super(id, user);
        this.id = id;
        this.user = user;
        this.expirationDate = expirationDate;
        this.number = number;
        this.cvv = cvv;
    }

    public String getId() {
        return this.id;
    }
}
