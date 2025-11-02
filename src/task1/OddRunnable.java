package task1;

public class OddRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " начал работу (нечётные числа):");

        for (int i = 1; i <= 9; i += 2) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                // Имитация работы потока
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(Thread.currentThread().getName() + " завершил работу.");
    }
}