package geometry2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure {
    private double width, height;

    public Rectangle(double x, double y, double width, double height, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x - width/2, y - height/2, width, height);

        if (selected) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeRect(x - width/2, y - height/2, width, height);
        }
    }

    @Override
    public boolean contains(double pointX, double pointY) {
        return pointX >= x - width/2 && pointX <= x + width/2 &&
                pointY >= y - height/2 && pointY <= y + height/2;
    }
}