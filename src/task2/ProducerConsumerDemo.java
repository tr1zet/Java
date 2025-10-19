package task2;

import java.util.Random;


public class ProducerConsumerDemo {

    public void demonstrate() {
        System.out.println("Задание 2: Producer-Consumer (Склад обуви) ");
        System.out.println();


        ShoeWarehouse warehouse = new ShoeWarehouse(7);
        Random random = new Random();


        Thread producerThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                String shoeType = ShoeWarehouse.PRODUCT_TYPES.get(
                        random.nextInt(ShoeWarehouse.PRODUCT_TYPES.size())
                );
                int quantity = random.nextInt(5) + 1;

                Order order = new Order(i, shoeType, quantity);
                warehouse.receiveOrder(order);

                try {
                    Thread.sleep(random.nextInt(500) + 500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("Producer завершил генерацию заказов.");
        }, "Producer");

        Thread consumerThread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Order order = warehouse.fulfillOrder();
                if (order == null) break;

                try {
                    Thread.sleep(random.nextInt(800) + 700);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("Consumer 1 завершил обработку заказов.");
        }, "Consumer-1");

        Thread consumerThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Order order = warehouse.fulfillOrder();
                if (order == null) break;

                try {
                    Thread.sleep(random.nextInt(800) + 700);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("Consumer 2 завершил обработку заказов.");
        }, "Consumer-2");


        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();

        try {
            producerThread.join();
            consumerThread1.join();
            consumerThread2.join();
        } catch (InterruptedException e) {
            System.err.println("Основной поток был прерван: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("Все заказы обработаны. Осталось на складе: " + warehouse.getOrderCount());
        System.out.println();
    }
}