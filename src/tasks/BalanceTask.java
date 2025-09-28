package tasks;

class Balance {
    private int leftWeight;
    private int rightWeight;

    public Balance() {
        leftWeight = 0;
        rightWeight = 0;
    }

    public void addRight(int weight) {
        rightWeight += weight;
    }

    public void addLeft(int weight) {
        leftWeight += weight;
    }

    public void result() {
        if (leftWeight == rightWeight) {
            System.out.println("=");
        } else if (leftWeight > rightWeight) {
            System.out.println("L");
        } else {
            System.out.println("R");
        }
    }

    public void showWeights() {
        System.out.println("Левая чаша: " + leftWeight + " | Правая чаша: " + rightWeight);
    }
}

public class BalanceTask {
    public static void run() {
        System.out.println("=== Задание 2: Весы ===");

        Balance scale = new Balance();

        scale.addLeft(15);
        scale.addRight(10);
        scale.showWeights();
        scale.result();

        Balance scale2 = new Balance();
        scale2.addLeft(5);
        scale2.addRight(7);
        scale2.showWeights();
        scale2.result();

        Balance scale3 = new Balance();
        scale3.addLeft(20);
        scale3.addRight(20);
        scale3.showWeights();
        scale3.result();
    }
}