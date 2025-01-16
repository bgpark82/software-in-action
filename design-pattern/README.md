To demonstrate how all major design patterns can be applied in a **single project** within one consistent Java application, let’s build an **e-commerce food ordering system**. In this project, we'll cover several crucial design patterns working together harmoniously, focusing on different components such as orders, payments, and inventory management.

### Scenario: **Food Ordering System**

**Context:** We are building a food ordering system where users can order food, which gets delivered based on selected delivery methods, and the system can handle different payment types. The system is highly modular and scalable using several design patterns.

---

### **1. Singleton Pattern**
- **Usage:** For managing a global configuration, such as the system database connection or API keys. This pattern ensures that there is only one instance of the configuration class.

```java
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private String databaseUrl;

    private ConfigurationManager() {
        databaseUrl = "jdbc:mysql://localhost:3306/foodorder";
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }
}
```

### **2. Factory Pattern**
- **Usage:** To create different types of payment methods dynamically (CreditCard, PayPal, etc.).

```java
public interface Payment {
    void pay(double amount);
}

public class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}

public class PayPalPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }
}

public class PaymentFactory {
    public static Payment createPayment(String type) {
        switch (type) {
            case "creditcard": return new CreditCardPayment();
            case "paypal": return new PayPalPayment();
            default: throw new IllegalArgumentException("Unknown payment type.");
        }
    }
}
```

### **3. Observer Pattern**
- **Usage:** When tracking order status. Customers get notified of the status changes in their orders.

```java
import java.util.ArrayList;
import java.util.List;

public interface Observer {
    void update(String status);
}

public class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String status) {
        System.out.println(name + " notified: Order status changed to " + status);
    }
}

public class Order {
    private List<Observer> observers = new ArrayList<>();
    private String status;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }
}
```

### **4. Strategy Pattern**
- **Usage:** For handling different delivery methods (bike, car, etc.). This pattern lets you choose the delivery strategy dynamically.

```java
public interface DeliveryStrategy {
    void deliver(String order);
}

public class BikeDelivery implements DeliveryStrategy {
    @Override
    public void deliver(String order) {
        System.out.println("Delivering " + order + " via Bike.");
    }
}

public class CarDelivery implements DeliveryStrategy {
    @Override
    public void deliver(String order) {
        System.out.println("Delivering " + order via Car.");
    }
}

public class DeliveryService {
    private DeliveryStrategy deliveryStrategy;

    public DeliveryService(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    public void executeDelivery(String order) {
        deliveryStrategy.deliver(order);
    }
}
```

### **5. Decorator Pattern**
- **Usage:** To add optional ingredients to food items dynamically, such as extra cheese or sauces.

```java
public interface Food {
    String prepare();
}

public class Pizza implements Food {
    @Override
    public String prepare() {
        return "Pizza";
    }
}

public abstract class FoodDecorator implements Food {
    protected Food decoratedFood;

    public FoodDecorator(Food decoratedFood) {
        this.decoratedFood = decoratedFood;
    }

    @Override
    public String prepare() {
        return decoratedFood.prepare();
    }
}

public class CheeseDecorator extends FoodDecorator {
    public CheeseDecorator(Food decoratedFood) {
        super(decoratedFood);
    }

    @Override
    public String prepare() {
        return super.prepare() + " with extra cheese";
    }
}
```

### **6. Command Pattern**
- **Usage:** For managing orders. You can encapsulate requests such as placing, updating, or canceling an order in a command.

```java
public interface Command {
    void execute();
}

public class PlaceOrderCommand implements Command {
    private Order order;

    public PlaceOrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute() {
        order.setStatus("Order placed");
    }
}

public class OrderService {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void processOrder() {
        command.execute();
    }
}
```

### **7. Facade Pattern**
- **Usage:** To simplify the interaction with different services (order processing, payment, and delivery).

```java
public class FoodOrderFacade {
    private PaymentFactory paymentFactory;
    private DeliveryService deliveryService;
    private Order order;

    public FoodOrderFacade(String paymentType, DeliveryStrategy deliveryStrategy) {
        paymentFactory = new PaymentFactory();
        deliveryService = new DeliveryService(deliveryStrategy);
        order = new Order();
    }

    public void placeOrder(String customerName, double amount) {
        Payment payment = paymentFactory.createPayment(paymentType);
        payment.pay(amount);
        deliveryService.executeDelivery("Order for " + customerName);
        order.setStatus("Order placed");
    }
}
```

### **8. Builder Pattern**
- **Usage:** To handle complex order creation with optional ingredients, delivery methods, and special instructions.

```java
public class FoodOrder {
    private String food;
    private String size;
    private String toppings;
    private boolean extraCheese;

    private FoodOrder(Builder builder) {
        this.food = builder.food;
        this.size = builder.size;
        this.toppings = builder.toppings;
        this.extraCheese = builder.extraCheese;
    }

    public static class Builder {
        private String food;
        private String size;
        private String toppings;
        private boolean extraCheese;

        public Builder(String food) {
            this.food = food;
        }

        public Builder size(String size) {
            this.size = size;
            return this;
        }

        public Builder toppings(String toppings) {
            this.toppings = toppings;
            return this;
        }

        public Builder extraCheese(boolean extraCheese) {
            this.extraCheese = extraCheese;
            return this;
        }

        public FoodOrder build() {
            return new FoodOrder(this);
        }
    }
}
```

---

### **Putting It All Together**
Here’s how the system works:

1. The **singleton** `ConfigurationManager` ensures there is only one configuration instance for the system.
2. **Factory** creates the appropriate `Payment` objects depending on the user’s choice (CreditCard, PayPal).
3. **Observer** notifies the `Customer` when the `Order` status changes.
4. **Strategy** determines the delivery method dynamically (Bike or Car).
5. **Decorator** allows dynamic additions of ingredients to food items.
6. **Command** encapsulates the logic for placing an order.
7. **Facade** simplifies the overall process by tying everything together and providing an easy interface to place orders.
8. **Builder** constructs complex food orders with optional properties such as size, toppings, and extras.

### Example Usage:

```java
public class Main {
    public static void main(String[] args) {
        // Place an order
        FoodOrderFacade orderFacade = new FoodOrderFacade("creditcard", new BikeDelivery());
        orderFacade.placeOrder("Alice", 25.50);

        // Add ingredients
        Food pizza = new CheeseDecorator(new Pizza());
        System.out.println(pizza.prepare());

        // Notify customer
        Customer customer = new Customer("Alice");
        Order order = new Order();
        order.addObserver(customer);
        order.setStatus("Ready for delivery");
    }
}
```

This approach shows how multiple design patterns can be implemented together in a cohesive project.