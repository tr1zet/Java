package tasks;

import geometry2d.Circle;
import geometry2d.Rectangle;
import geometry3d.Cylinder;

public class GeometryTask {
    public static void run() {
        System.out.println("=== Задание 6: Геометрия (2D и 3D фигуры) ===");

        Circle circle = new Circle(2.0);
        Rectangle rect = new Rectangle(3.0, 10.0);

        System.out.println(circle);
        System.out.println("Площадь круга: " + circle.area());
        System.out.println("Периметр круга: " + circle.perimeter());

        System.out.println(rect);
        System.out.println("Площадь прямоугольника: " + rect.area());
        System.out.println("Периметр прямоугольника: " + rect.perimeter());

        System.out.println("\n--- Цилиндры ---");

        Cylinder cylinder1 = new Cylinder(circle, 5.0);
        Cylinder cylinder2 = new Cylinder(rect, 6.0);

        System.out.println(cylinder1);
        System.out.println("Объем: " + cylinder1.volume());

        System.out.println(cylinder2);
        System.out.println("Объем: " + cylinder2.volume());
    }
}