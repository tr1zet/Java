package task2;

import java.security.SecureRandom;



public class ProducerConsumerDemo {
    public void demonstrate() {
        System.out.println("Задание 2: Producer-Consumer (Склад обуви) ");
        System.out.println();

        ShoeWarehouse warehouse = new ShoeWarehouse(7);
        SecureRandom random = new SecureRandom();

        Thread producerThread = createProducerThread(warehouse, random);
        Thread consumerThread1 = createConsumerThread(warehouse, random, "Consumer-1", 5);
        Thread consumerThread2 = createConsumerThread(warehouse, random, "Consumer-2", 5);

        startAndJoinThreads(producerThread, consumerThread1, consumerThread2);

        System.out.println("Все заказы обработаны. Осталось на складе: " + warehouse.getOrderCount());
    }

    private Thread createProducerThread(ShoeWarehouse warehouse, SecureRandom random) {
        return new Thread(() -> {
            generateOrders(warehouse, random, 10);
            System.out.println("Producer завершил генерацию заказов.");
        }, "Producer");
    }

    private Thread createConsumerThread(ShoeWarehouse warehouse, SecureRandom random, String name, int orderCount) {
        return new Thread(() -> {
            processOrders(warehouse, random, orderCount);
            System.out.println(name + " завершил обработку заказов.");
        }, name);
    }

    private void generateOrders(ShoeWarehouse warehouse, SecureRandom random, int orderCount) {
        for (int i = 1; i <= orderCount; i++) {
            String shoeType = getRandomShoeType(random);
            int quantity = random.nextInt(5) + 1;
            Order order = new Order(i, shoeType, quantity);
            warehouse.receiveOrder(order);
            sleepRandomTime(random, 500, 500);
        }
    }

    private void processOrders(ShoeWarehouse warehouse, SecureRandom random, int orderCount) {
        for (int i = 0; i < orderCount; i++) {
            Order order = warehouse.fulfillOrder();
            if (order == null) break;
            sleepRandomTime(random, 800, 700);
        }
    }

    private String getRandomShoeType(SecureRandom random) {
        return ShoeWarehouse.PRODUCT_TYPES.get(
                random.nextInt(ShoeWarehouse.PRODUCT_TYPES.size())
        );
    }

    private void sleepRandomTime(SecureRandom random, int range, int base) {
        try {
            Thread.sleep(random.nextInt(range) + base);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void startAndJoinThreads(Thread... threads) {
        // Запуск всех потоков
        for (Thread thread : threads) {
            thread.start();
        }

        // Ожидание завершения всех потоков
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Основной поток был прерван: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}