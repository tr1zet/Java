package tasks;

import java.util.ArrayList;
import java.util.List;

class OddEvenSeparator {
    private List<Integer> evenNumbers;
    private List<Integer> oddNumbers;

    public OddEvenSeparator() {
        evenNumbers = new ArrayList<>();
        oddNumbers = new ArrayList<>();
    }

    public void addNumber(int number) {
        if (number % 2 == 0) {
            evenNumbers.add(number);
        } else {
            oddNumbers.add(number);
        }
    }

    public void even() {
        System.out.println("Четные числа: " + evenNumbers);
    }

    public void odd() {
        System.out.println("Нечетные числа: " + oddNumbers);
    }
}

public class OddEvenSeparatorTask {
    public static void run() {
        System.out.println("=== Задание 4: Разделитель четных/нечетных ===");

        OddEvenSeparator separator = new OddEvenSeparator();

        separator.addNumber(1);
        separator.addNumber(2);
        separator.addNumber(5);
        separator.addNumber(4);
        separator.addNumber(3);
        separator.addNumber(7);
        separator.addNumber(8);
        separator.addNumber(9);
        separator.addNumber(10);

        separator.even();
        separator.odd();
    }
}