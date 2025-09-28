package tasks;

class Table {
    private int[][] data;
    private int rows;
    private int cols;

    public Table(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols];
    }

    public int getValue(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return data[row][col];
        } else {
            System.out.println("Ошибка: неверные индексы!");
            return -1;
        }
    }

    public void setValue(int row, int col, int value) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            data[row][col] = value;
        } else {
            System.out.println("Ошибка: неверные индексы!");
        }
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public double average() {
        if (rows == 0 || cols == 0) {
            return 0;
        }

        int sum = 0;
        int totalCells = rows * cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += data[i][j];
            }
        }

        return (double) sum / totalCells;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.append(data[i][j]);
                if (j < cols - 1) {
                    result.append(" ");
                }
            }
            if (i < rows - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }
}

public class TableTask {
    public static void run() {
        System.out.println("=== Задание 5: Таблица ===");

        Table table = new Table(3, 4);

        table.setValue(0, 0, 1);
        table.setValue(0, 1, 0);
        table.setValue(0, 2, 1);
        table.setValue(0, 3, 0);

        table.setValue(1, 0, 0);
        table.setValue(1, 1, 1);
        table.setValue(1, 2, 0);
        table.setValue(1, 3, 1);

        table.setValue(2, 0, 1);
        table.setValue(2, 1, 0);
        table.setValue(2, 2, 1);
        table.setValue(2, 3, 0);

        System.out.println("Таблица:");
        System.out.println(table);
        System.out.println("Среднее арифметическое: " + table.average());
    }
}