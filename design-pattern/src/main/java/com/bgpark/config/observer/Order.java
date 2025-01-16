package com.bgpark.config.observer;

import com.bgpark.config.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer
 * - Change status to multi observers
 */
public class Order {

    private List<Observer> observers = new ArrayList<>();
    private String status;
    private Command command;

    public void addObservers(Observer observer) {
        this.observers.add(observer);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void processOrder() {
        command.execute();
    }

    public void setStatus(String status) {
        this.status = status;
        System.out.println("Notifying to observers");
        this.notifyObservers(status);
    }

    private void notifyObservers(String status) {
        for (Observer observer : observers) {
            observer.notify(status);
        }
    }
}
