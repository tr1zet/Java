// Main.java
import srp.*;
import ocp.*;
import lsp.*;
import isp.*;
import dip.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ---------- S ----------
        System.out.println("$$$$ SRP Demo $$$$");
        ReportManager manager = new ReportManager(List.of(5, 10, 15, 20));
        manager.generateReport();

        // ---------- O ----------
        System.out.println("\n$$$$ OCP Demo $$$$");
        DiscountCalculator calculator = new DiscountCalculator();
        System.out.println("Regular: " + calculator.calculateDiscount(new RegularDiscount(), 1000));
        System.out.println("VIP: " + calculator.calculateDiscount(new VipDiscount(), 1000));
        System.out.println("Super VIP: " + calculator.calculateDiscount(new SuperVipDiscount(), 1000));
        System.out.println("Student: " + calculator.calculateDiscount(new StudentDiscount(), 1000));

        // ---------- L ----------
        System.out.println("\n$$$$ LSP Demo $$$$");
        displayFlyingBird(new Sparrow());
        displayNonFlyingBird(new Penguin());

        System.out.println("\n$$$$ All birds eating $$$$");
        displayBirdEating(new Sparrow());
        displayBirdEating(new Penguin());

        // ---------- I ----------
        System.out.println("\n$$$$ ISP Demo $$$$");
        PrintDevice oldPrinter = new OldPrinter("Amstrad PCW 8256;");
        ScanDevice scanner = new Scanner(" Canon CanoScan LiDE 400");
        MultiFunctionDevice modernPrinter = new ModernPrinter("Kyocera ECOSYS M2040dn");

        System.out.println("1. Работа со старым принтером:");
        System.out.println("Устройство: " + oldPrinter.getDeviceInfo());
        oldPrinter.print("Важный договор");

        System.out.println("\n2. Работа со сканером:");
        System.out.println("Устройство: " + scanner.getDeviceInfo());
        scanner.scan("Паспорт");

        System.out.println("\n3. Работа с современным МФУ:");
        System.out.println("Устройство: " + modernPrinter.getDeviceInfo());
        modernPrinter.print("Отчет за квартал");
        modernPrinter.scan("Документ");
        modernPrinter.fax("Срочное сообщение");

        System.out.println("\n4. Демонстрация полиморфизма:");
        Machine[] devices = {oldPrinter, scanner, modernPrinter};
        for (Machine device : devices) {
            System.out.println(" - " + device.getDeviceInfo());
        }

        // ---------- D ----------
        System.out.println("\n$$$$ DIP Demo $$$$");
        MessageSender emailSender = new EmailSender();
        MessageSender smsSender = new SmsSender();

        NotificationService emailService = new NotificationService(emailSender);
        NotificationService smsService = new NotificationService(smsSender);

        System.out.println("1. Отправка через Email:");
        emailService.send("Ваш заказ готов к выдаче!");

        System.out.println("\n2. Отправка через SMS:");
        smsService.send("Ваш код подтверждения: 0123456789");

        System.out.println("\n3. Смена отправителя на лету:");
        NotificationService flexibleService = new NotificationService(emailSender);
        flexibleService.send("Первое сообщение по email");
        flexibleService.setSender(smsSender);  // Теперь это работает
        flexibleService.send("Второе сообщение по SMS");

        System.out.println("\n4. Отправка через временного отправителя:");
        flexibleService.sendWith(new EmailSender(), "Срочное email-уведомление");
        flexibleService.sendWith(new SmsSender(), "Срочное SMS-уведомление");
    }

    public static void displayFlyingBird(FlyingBird bird) {
        bird.eat();
        bird.fly();
    }

    public static void displayNonFlyingBird(NonFlyingBird bird) {
        bird.eat();
        bird.walk();
        if (bird instanceof Penguin penguin) {
            penguin.swim();
        }
    }

    public static void displayBirdEating(Bird bird) {
        bird.eat();
    }
}