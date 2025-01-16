package com.bgpark.digital_wallet;

import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DigitalWalletTest {

    @Test
    void addUser() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        DigitalWallet wallet = new DigitalWallet();

        wallet.addUser(user);

        assertNotNull(wallet.getUser("1"));
    }

    @Test
    void addUserIsNotNull() {
        DigitalWallet wallet = new DigitalWallet();
        Exception exception = assertThrows(NullPointerException.class, () -> wallet.addUser(null));
        assertEquals(exception.getMessage(), "User must not be null");
    }

    @Test
    void addAccount() {
        DigitalWallet wallet = new DigitalWallet();
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        Account account = new Account("1", user, "1234", Account.Currency.GBP);
        wallet.addUser(user);

        wallet.addAccount(account);

        assertEquals(wallet.getAccount("1"), account);
    }

    @Test
    void addCreditCard() {
        DigitalWallet wallet = new DigitalWallet();
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        CreditCard creditCard = new CreditCard("1", user, LocalDateTime.now().plusYears(10), "1234", 123);
        wallet.addUser(user);

        wallet.addPaymentMethod(creditCard);

        assertEquals(wallet.getPaymentMethod("1"), creditCard);
    }

    @Test
    void addBankAccount() {
        DigitalWallet wallet = new DigitalWallet();
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        BankAccount bankAccount = new BankAccount("1", user, "1233-1231-1234-1234");
        wallet.addUser(user);

        wallet.addPaymentMethod(bankAccount);

        assertEquals(wallet.getPaymentMethod("1"), bankAccount);
    }

    @Test
    void addDuplicatePaymentMethodId() {
        DigitalWallet wallet = new DigitalWallet();
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        BankAccount bankAccount = new BankAccount("1", user, "1233-1231-1234-1234");
        CreditCard creditCard = new CreditCard("1", user, LocalDateTime.now().plusYears(10), "1234", 123);
        wallet.addUser(user);

        wallet.addPaymentMethod(bankAccount);
        DuplicateRequestException exception = assertThrows(DuplicateRequestException.class, () -> wallet.addPaymentMethod(creditCard));

        assertEquals(exception.getMessage(), "Duplicate payment method id : 1");
    }
}