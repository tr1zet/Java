package srp;

import java.time.LocalDateTime;

public class ReportPrinter {
    public void printReport(int sum, double avg) {
        System.out.println("**** Report ****");
        System.out.println("Cумма: " + sum);
        System.out.println("Среднее Значение: " + avg);
        System.out.println("Cгенерированный когда: " + LocalDateTime.now());
    }
}