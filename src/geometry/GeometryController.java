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

public class GeometryController {
    @FXML private Canvas canvas;

    private List<Figure> figures = new ArrayList<>();
    private Figure selectedFigure = null;
    private double lastMouseX, lastMouseY;
    private boolean isDragging = false;
    private int figureCount = 0;

    // Предопределенные цвета для абсолютной безопасности
    private static final Color[] PREDEFINED_COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.ORANGE, Color.PURPLE, Color.CYAN, Color.MAGENTA,
            Color.CHOCOLATE, Color.DARKGREEN, Color.ROYALBLUE, Color.CRIMSON,
            Color.DARKORANGE, Color.DEEPPINK, Color.TEAL, Color.NAVY,
            Color.LIME, Color.GOLD, Color.SIENNA, Color.INDIGO
    };

    // Предопределенные позиции для детерминированного размещения
    private static final double[][] POSITION_OFFSETS = {
            {0.2, 0.3}, {0.7, 0.2}, {0.3, 0.7}, {0.8, 0.6},
            {0.4, 0.4}, {0.6, 0.8}, {0.2, 0.6}, {0.7, 0.4},
            {0.5, 0.2}, {0.3, 0.5}, {0.8, 0.3}, {0.4, 0.8}
    };

    // Предопределенные размеры
    private static final double[] SIZES = {30, 40, 50, 60, 70, 80, 90, 100};

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
                    // Правая кнопка - меняем цвет на следующий в последовательности
                    figure.setColor(getNextColor(figure.getColor()));
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
            double x = getDeterministicPositionX();
            double y = getDeterministicPositionY();
            double radius = getDeterministicSize();
            Color color = getDeterministicColor();

            figures.add(new Circle(x, y, radius, color));
            figureCount++;
            redrawCanvas();
        }
    }

    @FXML
    private void addRectangle() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getDeterministicPositionX();
            double y = getDeterministicPositionY();
            double width = getDeterministicSize();
            double height = getDeterministicSize() * 0.8;
            Color color = getDeterministicColor();

            figures.add(new Rectangle(x, y, width, height, color));
            figureCount++;
            redrawCanvas();
        }
    }

    @FXML
    private void addSquare() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getDeterministicPositionX();
            double y = getDeterministicPositionY();
            double size = getDeterministicSize();
            Color color = getDeterministicColor();

            figures.add(new Square(x, y, size, color));
            figureCount++;
            redrawCanvas();
        }
    }

    @FXML
    private void addEllipse() {
        if (canvas.getWidth() > 0 && canvas.getHeight() > 0) {
            double x = getDeterministicPositionX();
            double y = getDeterministicPositionY();
            double radiusX = getDeterministicSize();
            double radiusY = getDeterministicSize() * 0.7;
            Color color = getDeterministicColor();

            figures.add(new Ellipse(x, y, radiusX, radiusY, color));
            figureCount++;
            redrawCanvas();
        }
    }

    @FXML
    private void clearCanvas() {
        figures.clear();
        selectedFigure = null;
        figureCount = 0;
        redrawCanvas();
    }

    // Детерминированные методы без случайных генераторов

    private double getDeterministicPositionX() {
        int index = figureCount % POSITION_OFFSETS.length;
        double offset = POSITION_OFFSETS[index][0];
        return 50 + offset * (canvas.getWidth() - 150);
    }

    private double getDeterministicPositionY() {
        int index = figureCount % POSITION_OFFSETS.length;
        double offset = POSITION_OFFSETS[index][1];
        return 50 + offset * (canvas.getHeight() - 150);
    }

    private double getDeterministicSize() {
        int index = figureCount % SIZES.length;
        return SIZES[index];
    }

    private Color getDeterministicColor() {
        int index = figureCount % PREDEFINED_COLORS.length;
        return PREDEFINED_COLORS[index];
    }

    private Color getNextColor(Color currentColor) {
        // Находим текущий цвет в массиве и возвращаем следующий
        for (int i = 0; i < PREDEFINED_COLORS.length; i++) {
            if (colorsEqual(PREDEFINED_COLORS[i], currentColor)) {
                return PREDEFINED_COLORS[(i + 1) % PREDEFINED_COLORS.length];
            }
        }
        return PREDEFINED_COLORS[0]; // fallback
    }

    private boolean colorsEqual(Color c1, Color c2) {
        return c1.getRed() == c2.getRed() &&
                c1.getGreen() == c2.getGreen() &&
                c1.getBlue() == c2.getBlue();
    }
}