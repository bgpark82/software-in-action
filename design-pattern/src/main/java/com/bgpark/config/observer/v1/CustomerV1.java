package com.bgpark.config.observer.v1;

import com.bgpark.config.observer.Observer;

public class CustomerV1 implements ObserverV1 {

    public void message(String message) {
        System.out.println(message);
    }
}
