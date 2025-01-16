package com.bgpark.config.strategy.v1;

/**
 * We can dynamically change delivery strategy in runtime
 */
public class Deliver {

    private final DeliveryStrategyV1 strategyV1;

    public Deliver(DeliveryStrategyV1 strategyV1) {
        this.strategyV1 = strategyV1;
    }

    public void deliver() {
        this.strategyV1.delivery();
    }
}
