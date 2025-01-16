package com.bgpark.digital_wallet;

import java.util.Objects;

/**
 * TODO: record research
 * - introduced Java 14
 * - default getter/setter
 * - hashCode, equals, toString
 * - immutable
*/

public class User {

    /** class level constant */
    private static final int PASSWORD_MINIMUM_LENGTH = 8;
    private static final String EMAIL_PATTERN = "@";

    private String id;
    private String name;
    private String email;
    private String password;
    private String accountId;

    /**
     * TODO: Static factory method vs BuilderPattern, Constructor
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
    public User(String id, String name, String email, String password) {
        checkId(id);
        checkEmail(email);
        checkPassword(password);

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = hashPassword(password);
    }

    public void addAccount(String accountId) {
        this.accountId = accountId;
    }

    private String hashPassword(String password) {
        return String.valueOf(password.hashCode());
    }

    private void checkId(String id) {
        if (Objects.isNull(id)) {
            throw new NullPointerException("Id must not be null");
        }
        /**
         * isBlank: Introduce Java 11
         */
        if (id.isBlank()) {
            throw new IllegalArgumentException("Id must not be empty");
        }
    }

    private void checkEmail(String email) {
        if (!email.contains(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void checkPassword(String password) {
        if (password.length() < PASSWORD_MINIMUM_LENGTH) {
            // formatted introduced since java 15
            throw new IllegalArgumentException("Password must be less than %s".formatted(PASSWORD_MINIMUM_LENGTH));
        }

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountId() {
        return accountId;
    }
}
