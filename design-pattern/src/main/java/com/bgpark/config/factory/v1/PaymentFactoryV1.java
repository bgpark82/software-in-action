package com.bgpark.config.factory.v1;

/**
 * encapsulate create instance
 */
public class PaymentFactoryV1 {

    public PaymentV1 create(String payment) {
        switch (payment) {
            case "PayPal":
                return new PayPalV1();
            case "CreditCard":
                return new CreditCardV1();
            default:
                throw new IllegalArgumentException();
        }
    }
}
