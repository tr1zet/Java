package tasks;

class Button {
    private int clickCount;

    public Button() {
        clickCount = 0;
    }

    public void click() {
        clickCount++;
        System.out.println("Количество нажатий: " + clickCount);
    }

    public void showClicks() {
        System.out.println("Текущее количество нажатий: " + clickCount);
    }
}

public class ButtonTask {
    public static void run() {
        System.out.println("=== Задание 1: Кнопка ===");

        Button myButton = new Button();

        myButton.click();
        myButton.click();
        myButton.click();
        myButton.click();
        myButton.click();
        myButton.click();
        myButton.click();

        myButton.showClicks();
    }
}