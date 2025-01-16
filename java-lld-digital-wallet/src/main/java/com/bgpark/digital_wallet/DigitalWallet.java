package com.bgpark.digital_wallet;

import com.sun.jdi.request.DuplicateRequestException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 1. Add User
 * 2. Add Account
 * 3. Add Payment method
 *      - Credit card
 *      - Bank account
 * 4. Deposit fund
 * 5. Transfer fund
 */
public class DigitalWallet {

    private Map<String, User> users;
    private Map<String, Account> accounts;
    private Map<String, PaymentMethod> paymentMethods;

    public DigitalWallet() {
        this.users = new HashMap<>();
        this.accounts = new HashMap<>();
        this.paymentMethods = new HashMap<>();
    }

    /**
     * Add user to digital wallet
     * @param user
     */
    public void addUser(User user) {
        checkUser(user);
        users.put(user.getId(), user);
    }

    /**
     * Get user from digital wallet
     * @param id
     * @return user
     */
    public User getUser(String id) {
        return this.users.get(id);
    }

    private void checkUser(User user) {
        if (Objects.isNull(user)) {
            throw new NullPointerException("User must not be null");
        }
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getId(), account);
        this.users.get(account.getUser()).addAccount(account.getId());
    }

    public Account getAccount(String id) {
        return this.accounts.get(id);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        checkPaymentMethod(paymentMethod);
        this.paymentMethods.put(paymentMethod.getId(), paymentMethod);
    }

    private void checkPaymentMethod(PaymentMethod paymentMethod) {
        if (this.paymentMethods.containsKey(paymentMethod.getId())) {
            throw new DuplicateRequestException("Duplicate payment method id : %s".formatted(paymentMethod.getId()));
        }
    }

    public synchronized void transfer(Account source, Account destination, BigDecimal amount, Account.Currency currency) {
        // 1. change currency
        if (source.getCurrency() != currency) {
            amount = CurrencyConverter.convert(amount, source.getCurrency(), currency);
        }
        source.withdraw(amount);

        // 2. withdraw from source account
        // amount = 10USD
        // source = USD
        // target = GBP
        // 3. change currency
        // 4. deposit to destination account
        if (destination.getCurrency() != currency) {
            amount = CurrencyConverter.convert(amount, currency, destination.getCurrency());
        }
        destination.deposit(amount);

        // 5. generate transaction id
        String transactionId = generateTransactionId();

        // 6. create transaction
        Transaction transaction = new Transaction(transactionId, source, destination, currency, amount);

        // 7. add transaction
        source.addTransaction(transaction);
        destination.addTransaction(transaction);
    }

    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * TODO: short keyword
     * @param id
     */
    public PaymentMethod getPaymentMethod(String id) {
        return this.paymentMethods.get(id);
    }
}
