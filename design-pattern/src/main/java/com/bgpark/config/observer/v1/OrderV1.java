package com.bgpark.config.observer.v1;

import com.bgpark.config.observer.Customer;
import com.bgpark.config.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class OrderV1 {

    private final List<ObserverV1> customers = new ArrayList<>();

    public void addCustomer(CustomerV1 customer) {
        customers.add(customer);
    }

    public void notifyAll(String message) {
        for (ObserverV1 customer : customers) {
            customer.message(message);
        }
    }
}
