package com.bgpark.digital_wallet;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test
    void createCreditCard() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        CreditCard creditCard = new CreditCard("1", user, LocalDateTime.now().plusYears(10), "1233-1231-1234-1234", 123);
        assertNotNull(creditCard);
    }
}