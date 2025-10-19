package task3;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWarehouse {

    public static final List<String> PRODUCT_TYPES = List.of(
            "Nike Air Max", "Nike air Force", "Puma Caven 2.0",
            "Reebok Classic", "Adidas run 70s 2.0", "Nike Balance 565"
    );

    private final Queue<Order> orders = new LinkedList<>();
    private final int maxCapacity;
    private final ExecutorService executorService;

    public ShoeWarehouse(int maxCapacity, int threadPoolSize) {
        this.maxCapacity = maxCapacity;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public synchronized void receiveOrder(Order order) {
        while (orders.size() >= maxCapacity) {
            try {
                System.out.println("Склад заполнен. Producer ждёт...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer был прерван: " + e.getMessage());
                return;
            }
        }

        orders.offer(order);
        System.out.println("Добавлен заказ: " + order + " | Всего заказов: " + orders.size());
        executorService.submit(this::processOrder);

        notifyAll();
    }

    private Order processOrder() {
        Order order = null;

        synchronized (this) {
            while (orders.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.err.println("Обработчик был прерван: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            order = orders.poll();
            System.out.println("Обработан заказ: " + order + " | Осталось заказов: " + orders.size());
            notifyAll();
        }



        return order;
    }

    public synchronized int getOrderCount() {
        return orders.size();
    }

    public void shutdown() {
        executorService.shutdown();
    }
}