import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Выберите действие: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        executeReflectionTask();
                        break;
                    case 2:
                        executeFileSystemTask(scanner);
                        break;
                    case 3:
                        executeBothTasks(scanner);
                        break;
                    case 0:
                        System.out.println("Выход из программы...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } else {
                System.out.println("Пожалуйста, введите число.");
                scanner.nextLine(); // clear invalid input
            }

            System.out.println("\n" + "=".repeat(50) + "\n");
        }
    }

    private static void printMenu() {
        System.out.println(" ГЛАВНОЕ МЕНЮ ");
        System.out.println("1. Задание 1 - Рефлексия и аннотации");
        System.out.println("2. Задание 2 - Работа с файловой системой");
        System.out.println("3. Выполнить оба задания");
        System.out.println("0. Выход");
    }

    private static void executeReflectionTask() {
        System.out.println("\n--- Задание 1: Рефлексия и аннотации ---");
        Invoker invoker = new Invoker();
        invoker.invokeAnnotatedMethods();
    }

    private static void executeFileSystemTask(Scanner scanner) {
        System.out.println("\n--- Задание 2: Работа с файловой системой ---");
        System.out.print("Введите вашу фамилию: ");
        String surname = scanner.nextLine();
        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();

        FileSystemTask fileTask = new FileSystemTask(surname, name);
        fileTask.executeFileSystemTask();
    }

    private static void executeBothTasks(Scanner scanner) {
        System.out.println("\n--- Выполнение обоих заданий ---");

        // Задание 1
        System.out.println("\n[ЗАДАНИЕ 1]");
        executeReflectionTask();

        // Задание 2
        System.out.println("\n[ЗАДАНИЕ 2]");
        System.out.print("Введите вашу фамилию: ");
        String surname = scanner.nextLine();
        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();

        FileSystemTask fileTask = new FileSystemTask(surname, name);
        fileTask.executeFileSystemTask();
    }
}