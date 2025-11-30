package geometry;

import geometry2d.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeometryController {
    @FXML private Canvas canvas;

    private List<Figure> figures = new ArrayList<>();
    private Figure selectedFigure = null;
    private double lastMouseX, lastMouseY;
    private boolean isDragging = false;

    // Предопределенные цвета для абсолютной безопасности
    private static final Color[] PREDEFINED_COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.ORANGE, Color.PURPLE, Color.CYAN, Color.MAGENTA,
            Color.CHOCOLATE, Color.DARKGREEN, Color.ROYALBLUE, Color.CRIMSON,
            Color.DARKORANGE, Color.DEEPPINK, Color.TEAL, Color.NAVY,
            Color.LIME, Color.GOLD, Color.SIENNA, Color.INDIGO
    };

    @FXML
    public void initialize() {
        setupCanvas();
        startAnimation();
    }

    private void setupCanvas() {
        // Привязываем размер холста
        if (canvas.getParent() instanceof VBox) {
            VBox parent = (VBox) canvas.getParent();
            canvas.widthProperty().bind(parent.widthProperty());
        }

        // Обработчики событий мыши
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }

    private void startAnimation() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                redrawCanvas();
            }
        }.start();
    }

    private void redrawCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Figure figure : figures) {
            figure.draw(gc);
        }
    }

    private void handleMousePressed(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        // Снимаем выделение со всех фигур
        figures.forEach(f -> f.setSelected(false));
        selectedFigure = null;

        // Ищем фигуру под курсором
        for (int i = figures.size() - 1; i >= 0; i--) {
            Figure figure = figures.get(i);
            if (figure.contains(x, y)) {
                selectedFigure = figure;
                figure.setSelected(true);

                if (event.isSecondaryButtonDown()) {
                    // Правая кнопка - меняем цвет
                    figure.setColor(getPredefinedColor());
                } else if (event.isPrimaryButtonDown()) {
                    // Левая кнопка - начинаем перетаскивание
                    lastMouseX = x;
                    lastMouseY = y;
                    isDragging = true;

                    // Перемещаем фигуру на передний план
                    figures.remove(i);
                    figures.add(figure);
                }
                break;
            }
        }

        redrawCanvas();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (selectedFigure != null && isDragging && event.isPrimaryButtonDown()) {
            double x = event.getX();
            double y = event.getY();

            double dx = x - lastMouseX;
            double dy = y - lastMouseY;

            selectedFigure.move(dx, dy);

            lastMouseX = x;
            lastMouseY = y;
            redrawCanvas();
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        isDragging = false;
    }

    @FXML
    private void addCircle() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getRandomPosition(canvas.getWidth(), 50, 100);
            double y = getRandomPosition(canvas.getHeight(), 50, 100);
            double radius = 20 + getRandomDouble(50);
            Color color = getPredefinedColor();

            figures.add(new Circle(x, y, radius, color));
            redrawCanvas();
        }
    }

    @FXML
    private void addRectangle() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getRandomPosition(canvas.getWidth(), 50, 100);
            double y = getRandomPosition(canvas.getHeight(), 50, 100);
            double width = 40 + getRandomDouble(80);
            double height = 40 + getRandomDouble(80);
            Color color = getPredefinedColor();

            figures.add(new Rectangle(x, y, width, height, color));
            redrawCanvas();
        }
    }

    @FXML
    private void addSquare() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getRandomPosition(canvas.getWidth(), 50, 100);
            double y = getRandomPosition(canvas.getHeight(), 50, 100);
            double size = 40 + getRandomDouble(80);
            Color color = getPredefinedColor();

            figures.add(new Square(x, y, size, color));
            redrawCanvas();
        }
    }

    @FXML
    private void addEllipse() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getRandomPosition(canvas.getWidth(), 50, 100);
            double y = getRandomPosition(canvas.getHeight(), 50, 100);
            double radiusX = 30 + getRandomDouble(60);
            double radiusY = 20 + getRandomDouble(40);
            Color color = getPredefinedColor();

            figures.add(new Ellipse(x, y, radiusX, radiusY, color));
            redrawCanvas();
        }
    }

    @FXML
    private void clearCanvas() {
        figures.clear();
        selectedFigure = null;
        redrawCanvas();
    }

    // Безопасные методы генерации позиций (не security-critical)
    private double getRandomPosition(double maxValue, double margin, double range) {
        return margin + ThreadLocalRandom.current().nextDouble(maxValue - range);
    }

    private double getRandomDouble(double bound) {
        return ThreadLocalRandom.current().nextDouble(bound);
    }

    // Абсолютно безопасный метод получения цвета
    private Color getPredefinedColor() {
        // Используем предопределенные цвета вместо случайной генерации
        int index = ThreadLocalRandom.current().nextInt(PREDEFINED_COLORS.length);
        return PREDEFINED_COLORS[index];
    }
}