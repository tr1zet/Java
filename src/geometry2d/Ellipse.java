package geometry2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends Figure {
    private double radiusX, radiusY;

    public Ellipse(double x, double y, double radiusX, double radiusY, Color color) {
        super(x, y, color);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radiusX, y - radiusY, radiusX * 2, radiusY * 2);

        if (selected) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(x - radiusX, y - radiusY, radiusX * 2, radiusY * 2);
        }
    }

    @Override
    public boolean contains(double pointX, double pointY) {
        double dx = (pointX - x) / radiusX;
        double dy = (pointY - y) / radiusY;
        return dx * dx + dy * dy <= 1;
    }
}