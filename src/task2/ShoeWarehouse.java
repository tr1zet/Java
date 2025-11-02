package task2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ShoeWarehouse {
    public static final List<String> PRODUCT_TYPES = List.of(
            "Nike Air Max", "Nike air Force", "Puma Caven 2.0",
            "Reebok Classic", "Adidas run 70s 2.0", "Nike Balance 565"
    );

    private final Queue<Order> orders = new LinkedList<>();
    private final int maxCapacity;

    public ShoeWarehouse(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void receiveOrder(Order order) {
        while (orders.size() >= maxCapacity) {
            try {
                System.out.println("Склад заполнен. Producer ждёт...");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Producer был прерван: " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }

        orders.offer(order);
        System.out.println("Добавлен заказ: " + order + " | Всего заказов: " + orders.size());
        notifyAll();
    }

    public synchronized Order fulfillOrder() {
        while (orders.isEmpty()) {
            try {
                System.out.println("Склад пуст. Consumer ждёт...");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Consumer был прерван: " + e.getMessage());
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Order order = orders.poll();
        System.out.println("Обработан заказ: " + order + " | Осталось заказов: " + orders.size());


        notifyAll();
        return order;
    }


    public synchronized int getOrderCount() {
        return orders.size();
    }
}