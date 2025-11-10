package srp;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ReportSaver {
    public void saveToFile(int sum, double avg) {
        try (FileWriter writer = new FileWriter("report.txt")) {
            writer.write("**** Report ****\n");
            writer.write("Сумма: " + sum + "\n");
            writer.write("Среднее значение: " + avg + "\n");
            writer.write("Cгенерированный когда: " + LocalDateTime.now() + "\n");
            System.out.println("Отчет сохраненный в  report.txt");
        } catch (IOException e) {
            System.out.println("Файл отчета об ошибке при записи: " + e.getMessage());
        }
    }
}