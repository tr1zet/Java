package tasks;

class Bell {
    private String nextSound = "ding";

    public void sound() {
        System.out.println(nextSound);

        if (nextSound.equals("ding")) {
            nextSound = "dong";
        } else {
            nextSound = "ding";
        }
    }
}

public class BellTask {
    public static void run() {
        System.out.println("=== Задание 3: Колокол ===");

        Bell bell = new Bell();

        bell.sound();
        bell.sound();
        bell.sound();
        bell.sound();
        bell.sound();
        bell.sound();
        bell.sound();
    }
}