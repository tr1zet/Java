package task3;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {
    public void demonstrate() {
        System.out.println("Задание 3: ExecutorService ");
        System.out.println();
        ShoeWarehouse warehouse = new ShoeWarehouse(5, 3);
        SecureRandom random = new SecureRandom();

        try (ExecutorService producerExecutor = Executors.newFixedThreadPool(2)) {
            for (int p = 1; p <= 2; p++) {
                final int producerId = p;
                producerExecutor.submit(() -> {
                    for (int i = 1; i <= 8; i++) {
                        String shoeType = ShoeWarehouse.PRODUCT_TYPES.get(
                                random.nextInt(ShoeWarehouse.PRODUCT_TYPES.size())
                        );
                        int quantity = random.nextInt(5) + 1;
                        Order order = new Order(i + (producerId - 1) * 8,
                                shoeType + " (Producer-" + producerId + ")", quantity);
                        warehouse.receiveOrder(order);
                        try {
                            Thread.sleep(random.nextInt(400) + 300);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    System.out.println("Producer " + producerId + " завершил работу.");
                });
            }

            producerExecutor.shutdown();
            try {
                if (!producerExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                    producerExecutor.shutdownNow();
                    if (!producerExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                        System.err.println("ExecutorService не завершил работу");
                    }
                }
                Thread.sleep(2000);
                warehouse.shutdown();
            } catch (InterruptedException e) {
                System.err.println("Ожидание завершения было прервано: " + e.getMessage());
                producerExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Все задачи завершены с использованием ExecutorService.");
        System.out.println();
    }
}