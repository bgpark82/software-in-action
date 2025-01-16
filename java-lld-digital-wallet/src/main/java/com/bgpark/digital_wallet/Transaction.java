package com.bgpark.digital_wallet;

import java.math.BigDecimal;

public class Transaction {

    private String transactionId;
    private Account source;
    private Account destination;
    private Account.Currency currency;
    private BigDecimal amount;

    public Transaction(String transactionId, Account source, Account destination, Account.Currency currency, BigDecimal amount) {
        this.transactionId = transactionId;
        this.source = source;
        this.destination = destination;
        this.currency = currency;
        this.amount = amount;
    }
}
