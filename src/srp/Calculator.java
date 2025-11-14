package srp;

import java.util.List;

public class Calculator {
    public int calculateSum(List<Integer> data) {
        int sum = 0;
        for (int n : data) {
            sum += n;
        }
        return sum;
    }

    public double calculateAverage(List<Integer> data) {
        if (data.isEmpty()) return 0;
        return (double) calculateSum(data) / data.size();
    }
}
