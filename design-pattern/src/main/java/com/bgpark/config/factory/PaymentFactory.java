package com.bgpark.config.factory;

/**
 * Factory
 * - Create difference type of instance dynamically
 * - If else, switch case,
 */
public class PaymentFactory {

    public static Payment createPayment(String type) {
        switch (type) {
            case "creditCard":
                return new CreditCard();
            case "paypal":
                return new PayPal();
            default:
                throw new IllegalArgumentException("Unknown payment type=%s".formatted(type));
        }
    }
}
