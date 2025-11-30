package calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CalculatorController {
    @FXML private TextField display;

    private Calculator calculator;
    private boolean startNewNumber = true;
    private String lastOperator = "";
    private double result = 0;

    @FXML
    public void initialize() {
        calculator = new Calculator();
        setupKeyboardHandlers();
    }

    private void setupKeyboardHandlers() {
        display.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!isValidInput(character)) {
                event.consume();
            }
        });

        display.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                case EQUALS:
                    calculateResult();
                    break;
                case ESCAPE:
                    clear();
                    break;
                case BACK_SPACE:
                    backspace();
                    break;
            }
        });
    }

    private boolean isValidInput(String input) {
        return input.matches("[0-9+\\-*/.=]") || input.equals("\r");
    }

    @FXML
    private void handleButtonAction(javafx.event.ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        switch (buttonText) {
            case "C":
                clear();
                break;
            case "=":
                calculateResult();
                break;
            case "←":
                backspace();
                break;
            case "+/-":
                toggleSign();
                break;
            default:
                appendToDisplay(buttonText);
                break;
        }
    }

    private void appendToDisplay(String text) {
        if (startNewNumber) {
            display.setText("");
            startNewNumber = false;
        }

        String currentText = display.getText();

        // Проверка на корректность ввода
        if (text.matches("[+\\-*/]")) {
            if (currentText.isEmpty() || currentText.matches(".*[+\\-*/]$")) {
                return;
            }
        } else if (text.equals(".")) {
            if (currentText.contains(".") && !currentText.matches(".*[+\\-*/].*")) {
                return;
            }
        }

        display.setText(currentText + text);
    }

    private void clear() {
        display.setText("0");
        calculator.clear();
        startNewNumber = true;
        lastOperator = "";
        result = 0;
    }

    private void backspace() {
        String currentText = display.getText();
        if (!currentText.isEmpty() && !currentText.equals("0")) {
            String newText = currentText.substring(0, currentText.length() - 1);
            display.setText(newText.isEmpty() ? "0" : newText);
        }
    }

    private void toggleSign() {
        String currentText = display.getText();
        if (!currentText.isEmpty() && !currentText.equals("0")) {
            if (currentText.startsWith("-")) {
                display.setText(currentText.substring(1));
            } else {
                display.setText("-" + currentText);
            }
        }
    }

    private void calculateResult() {
        try {
            String expression = display.getText();
            double result = calculator.evaluate(expression);
            display.setText(formatResult(result));
            startNewNumber = true;
        } catch (ArithmeticException e) {
            display.setText("Ошибка: деление на 0");
            startNewNumber = true;
        } catch (Exception e) {
            display.setText("Ошибка");
            startNewNumber = true;
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%.6f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }
}