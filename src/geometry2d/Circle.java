package geometry2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Figure {
    private double radius;

    public Circle(double x, double y, double radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        if (selected) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    @Override
    public boolean contains(double pointX, double pointY) {
        double dx = pointX - x;
        double dy = pointY - y;
        return dx * dx + dy * dy <= radius * radius;
    }
}