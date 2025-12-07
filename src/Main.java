import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" Выбор приложения");
        System.out.println("1 - Калькулятор");
        System.out.println("2 - Геометрические фигуры");
        System.out.println("3 - Выход");
        System.out.print("Выберите приложение (1-3): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.println("Запуск калькулятора...");
                calculator.CalculatorApp.main(args);
                break;
            case "2":
                System.out.println("Запуск Geometry приложения...");
                geometry.GeometryApp.main(args);
                break;
            case "3":
                System.out.println("Выход...");
                break;
            default:
                System.out.println("Неверный выбор. Запуск калькулятора по умолчанию...");
                calculator.CalculatorApp.main(args);
                break;
        }

        scanner.close();
    }
}