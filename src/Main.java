package src;

import src.Tasks.Task1;
import src.Tasks.Task2;
import src.Tasks.Task3;
import src.Tasks.Task4;
import src.Tasks.Task5;
import src.utils.Input;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n  МЕНЮ ВЫБОРА ЗАДАНИЙ    ");
            System.out.println("1. Работа с Collections  ");
            System.out.println("2. Генератор простых чисел ");
            System.out.println("3. Работа с Human  ");
            System.out.println("4. Частота слов в тексте ");
            System.out.println("5. Обмен ключей и значений в Map ");
            System.out.println("0. Выход");

            int choice = Input.getIntInput("Выберите задание: ");

            switch (choice) {
                case 1:
                    Task1.execute();
                    break;
                case 2:
                    Task2.execute();
                    break;
                case 3:
                    Task3.execute();
                    break;
                case 4:
                    Task4.execute();
                    break;
                case 5:
                    Task5.execute();
                    break;
                case 0:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }
        }
    }
}