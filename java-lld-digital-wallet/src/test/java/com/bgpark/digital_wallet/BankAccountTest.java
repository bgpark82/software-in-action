package com.bgpark.digital_wallet;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void createBankAccount() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        BankAccount bankAccount = new BankAccount("1", user, "1233-1231-1234-1234");
        assertNotNull(bankAccount);
    }
}