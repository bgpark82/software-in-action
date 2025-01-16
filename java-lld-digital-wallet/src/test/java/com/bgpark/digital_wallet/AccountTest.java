package com.bgpark.digital_wallet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void createAccount() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        Account account = new Account("1", user, "1234", Account.Currency.EUR);
        assertNotNull(account);
    }

    @Test
    void nullCurrency() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        assertThrows(NullPointerException.class, () -> new Account("1", user, "1234", null));
    }

    @Test
    void deposit() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        Account account = new Account("1", user, "1234", Account.Currency.GBP);

        account.deposit(new BigDecimal(1000L));

        assertEquals(account.getBalance(), new BigDecimal(1000L));
    }

    @Test
    void depositMore() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        Account account = new Account("1", user, "1234", Account.Currency.GBP);

        account.deposit(new BigDecimal(1000L));
        account.deposit(new BigDecimal(1000L));
        account.deposit(new BigDecimal(1000L));

        assertEquals(account.getBalance(), new BigDecimal(3000L));
    }

    @Test
    void depositNull() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        Account account = new Account("1", user, "1234", Account.Currency.GBP);

        account.deposit(new BigDecimal(1000L));
        account.deposit(null);

        assertEquals(account.getBalance(), new BigDecimal(3000L));
    }

    /**
     * synchronize: 449
     *
     * @throws InterruptedException
     */
    @Test
    void threadSafe() throws InterruptedException {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        Account account = new Account("1", user, "1234", Account.Currency.GBP);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        long start = System.nanoTime();
        for (int i = 0; i < 3_000_000; i++) {
            executorService.submit(() -> account.deposit(BigDecimal.valueOf(1000L)));
        }
        long end = System.nanoTime();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start));

        assertEquals(account.getBalance(), BigDecimal.valueOf(1000L * 3_000_000));
    }
}