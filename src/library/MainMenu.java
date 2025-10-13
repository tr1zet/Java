package library;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService service = new LibraryService();

        System.out.println(" Система для Библиотеки ");
        System.out.println("Загружено посетителей: " + service.getVisitors().size());

        while (true) {
            printMenu();
            System.out.print("Выберите функцию (1-8): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера

                switch (choice) {
                    case 1:
                        service.task1();
                        break;
                    case 2:
                        service.task2();
                        break;
                    case 3:
                        service.task3();
                        break;
                    case 4:
                        service.task4();
                        break;
                    case 5:
                        service.task5();
                        break;
                    case 6:
                        service.task6();
                        break;
                    case 7:
                        System.out.println("Выход из программы...");
                        scanner.close();
                        return;
                    case 8:
                        executeAllTasks(service);
                        break;
                    default:
                        System.out.println("Неверный выбор! Попробуйте снова.\n");
                }
            } else {
                System.out.println("Введите число от 1 до 8!\n");
                scanner.nextLine(); // очистка неверного ввода
            }
        }
    }

    private static void printMenu() {
        System.out.println("МЕНЮ:");
        System.out.println("1 - Список посетителей и их количество");
        System.out.println("2 - Уникальные книги в избранном");
        System.out.println("3 - Книги отсортированные по году");
        System.out.println("4 - Поиск книг Jane Austen");
        System.out.println("5 - Максимальное количество книг");
        System.out.println("6 - SMS рассылка");
        System.out.println("7 - Выход");
        System.out.println("8 - Выполнить все задания");
        System.out.println("----------------------------");
    }

    private static void executeAllTasks(LibraryService service) {
        System.out.println("\n Выполнение всех заданий \n");
        service.task1();
        service.task2();
        service.task3();
        service.task4();
        service.task5();
        service.task6();
        System.out.println(" Все заданий выполнены \n");
    }
}