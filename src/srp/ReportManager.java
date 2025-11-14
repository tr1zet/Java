package srp;

import java.util.List;

public class ReportManager {
    private final List<Integer> data;
    private final Calculator calculator;
    private final ReportPrinter printer;
    private final ReportSaver saver;

    public ReportManager(List<Integer> data) {
        this.data = data;
        this.calculator = new Calculator();
        this.printer = new ReportPrinter();
        this.saver = new ReportSaver();
    }

    public void generateReport() {
        int sum = calculator.calculateSum(data);
        double avg = calculator.calculateAverage(data);

        printer.printReport(sum, avg);
        saver.saveToFile(sum, avg);
    }
}