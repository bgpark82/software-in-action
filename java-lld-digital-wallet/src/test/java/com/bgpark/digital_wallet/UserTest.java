package com.bgpark.digital_wallet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        assertEquals("1", user.getId());
        assertEquals("bgpark", user.getName());
        assertEquals("bgpark82@gmail.com", user.getEmail());
        assertEquals(String.valueOf("password".hashCode()), user.getPassword());
    }

    /**
     * ID
     * - Unique
     * - Not null
     * - Not empty
     * Name
     * - Not null
     * - Not empty
     * Email
     * - Not null
     * - Not empty
     * - Email format
     * Password
     * - Not null
     * - Not empty
     * - Encrypted
     * - Length 8 ~ 20
     */
    @Test
    void idShouldNotBeNull() {
        assertThrows(NullPointerException.class,
                () -> new User(null, "bgpark", "bgpark82@gmail.com", "password"));
    }

    @Test
    void idShouldNotBeEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new User("", "bgpark", "bgpark82@gmail.com", "password"));
        assertThrows(IllegalArgumentException.class,
                () -> new User(" ", "bgpark", "bgpark82@gmail.com", "password"));
    }

    @Test
    void invalidEmailFormat() {
        assertThrows(IllegalArgumentException.class,
                () -> new User("1", "bgpark", "bgpark82", "password"));
    }

    @Test
    void invalidPasswordLength() {
        assertThrows(IllegalArgumentException.class,
                () -> new User("1", "bgpark", "bgpark82@gmail.com", "passw"));
    }

    @Test
    void IsHashedPassword() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        assertEquals(user.getPassword(), String.valueOf("password".hashCode()));
    }

    @Test
    void addAccount() {
        User user = new User("1", "bgpark", "bgpark82@gmail.com", "password");
        String accountId = "1";

        user.addAccount(accountId);

        assertEquals(user.getPassword(), String.valueOf("password".hashCode()));
    }
}