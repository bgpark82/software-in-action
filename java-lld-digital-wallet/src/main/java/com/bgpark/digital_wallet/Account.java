package com.bgpark.digital_wallet;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Acount
 */
public class Account {

    private String id;
    private User user;
    private String number;
    private Currency currency;
    private BigDecimal balance;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String id, User user, String number, Currency currency) {
        checkCurrency(currency);

        this.id = id;
        this.user = user;
        this.number = number;
        this.balance = BigDecimal.ZERO;
        this.currency = currency;
    }

    /**
     * ID
     * - Not null
     * - Not empty
     * - Unique
     * User
     * - Not null
     * Number
     * - Not null
     * - Not empty
     * - Unique
     * Currency
     * - Not null
     * - Not empty
     * - EUR, USD, WON
     */

    private void checkCurrency(Currency currency) {
        if (Objects.isNull(currency)) {
            throw new NullPointerException("Currency must not be null");
        }
    }

    /**
     * TODO: BigDecimal: immutable
     * @param amount
     */
    public void deposit(BigDecimal amount) {
        if (Objects.isNull(amount)) {
            throw new NullPointerException("Deposit amount can not be null");
        }

        /**
         * synchronous
         * atomic
         * reentrant lock
         * read write lock
         * stamped lock
         * concurrent data structures
         * immutable object
         * thread local variable
         */
        synchronized (this) {
            this.balance = this.balance.add(amount);
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public synchronized void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance.subtract(amount);
        } else {
            throw new RuntimeException("Insufficient balance!");
        }
    }

    /**
     * TODO: better than synchronous
     * @param transaction
     */
    public synchronized void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * TODO: Enum
     */
    public enum Currency {
        USD("United States"),
        GBP("British"),
        EUR("Europe");

        String description;

        Currency(String description) {
            this.description = description;
        }
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Currency getCurrency() {
        return currency;
    }
}
