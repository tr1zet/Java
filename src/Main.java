import task1.ThreadCreationDemo;
import task2.ProducerConsumerDemo;
import task3.ExecutorServiceDemo;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Выберите задание (1-3) или 0 для выхода: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 0:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        return;

                    case 1:
                        ThreadCreationDemo task1 = new ThreadCreationDemo();
                        task1.demonstrate();
                        break;

                    case 2:
                        ProducerConsumerDemo task2 = new ProducerConsumerDemo();
                        task2.demonstrate();
                        break;

                    case 3:
                        ExecutorServiceDemo task3 = new ExecutorServiceDemo();
                        task3.demonstrate();
                        break;

                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }

                System.out.println("Нажмите Enter для продолжения...");
                scanner.nextLine();

            } else {
                System.out.println("Пожалуйста, введите число.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите пункт для работы!!!!!");
        System.out.println("1. Создание потоков (Thread и Runnable)");
        System.out.println("2. Producer-Consumer (Склад обуви)");
        System.out.println("3. ExecutorService");
        System.out.println("0. Выход");
    }
}