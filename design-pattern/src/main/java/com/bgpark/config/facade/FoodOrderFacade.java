package com.bgpark.config.facade;

import com.bgpark.config.strategy.Delivery;
import com.bgpark.config.strategy.DeliveryStrategy;
import com.bgpark.config.factory.Payment;
import com.bgpark.config.factory.PaymentFactory;
import com.bgpark.config.observer.Order;

import java.math.BigDecimal;

/**
 * Facade
 * - Simplify the interaction with different services
 */
public class FoodOrderFacade {

    private PaymentFactory paymentFactory;
    private Delivery delivery;
    private Order order;

    public FoodOrderFacade(DeliveryStrategy deliveryStrategy) {
        this.paymentFactory = new PaymentFactory();
        this.delivery = new Delivery(deliveryStrategy);
        this.order = new Order();
    }

    public void order(String paymentType, BigDecimal amount) {
        Payment payment = paymentFactory.createPayment(paymentType);
        payment.pay(amount);
        delivery.deliver("ORDERED");
        order.setStatus("ORDER!");

    }
}
