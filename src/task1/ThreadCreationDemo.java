package task1;


public class ThreadCreationDemo {

    public void demonstrate() {
        System.out.println("Задание 1: Создание потоков ");
        System.out.println();

        Thread evenThread = new EvenThread();
        evenThread.setName("EvenThread");

        Thread oddThread = new Thread(new OddRunnable(), "OddThread");

        System.out.println("Запуск потоков...");
        evenThread.start();
        oddThread.start();

        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            System.err.println("Основной поток был прерван: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        System.out.println("Оба потока завершили работу.");
        System.out.println();
    }
}