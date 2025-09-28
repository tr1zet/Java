package menu;

import tasks.ButtonTask;
import tasks.BalanceTask;
import tasks.BellTask;
import tasks.OddEvenSeparatorTask;
import tasks.TableTask;
import tasks.FileAnalyzerTask;
import tasks.StudentGradesTask;
import tasks.GeometryTask;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Задание 1 - Кнопка");
            System.out.println("2. Задание 2 - Весы");
            System.out.println("3. Задание 3 - Колокол");
            System.out.println("4. Задание 4 - Разделитель четных/нечетных");
            System.out.println("5. Задание 5 - Таблица");
            System.out.println("6. Задание 6 - Анализ файла");
            System.out.println("7. Задание 7 - Анализ оценок студентов");
            System.out.println("8. Задание 8 - Геометрия (2D/3D фигуры)");
            System.out.println("0. Выход");
            System.out.print("Выберите задание: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ButtonTask.run();
                    break;
                case 2:
                    BalanceTask.run();
                    break;
                case 3:
                    BellTask.run();
                    break;
                case 4:
                    OddEvenSeparatorTask.run();
                    break;
                case 5:
                    TableTask.run();
                    break;
                case 6:
                    FileAnalyzerTask.run();
                    break;
                case 7:
                    StudentGradesTask.run();
                    break;
                case 8:
                    GeometryTask.run();
                    break;
                case 0:
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }

            System.out.println("\nНажмите Enter для продолжения...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }
}