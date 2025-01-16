package com.bgpark.config.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {

    @Test
    void name() {
        Payment payment = PaymentFactory.createPayment("paypal");
        assertEquals(payment.getClass().getSimpleName(), "PayPal");
    }
}