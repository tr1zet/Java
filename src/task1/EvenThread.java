package task1;

public class EvenThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " начал работу (чётные числа):");

        for (int i = 2; i <= 10; i += 2) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Поток был прерван: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            });
        }
        System.out.println(Thread.currentThread().getName() + " завершил работу.");
    }
}