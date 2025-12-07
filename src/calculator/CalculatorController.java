package calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CalculatorController {
    @FXML private TextField display;

    private Calculator calculator;
    private boolean startNewNumber = true;

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
        if (input == null || input.isEmpty()) return false;

        char c = input.charAt(0);
        return Character.isDigit(c) ||
                c == '+' || c == '-' || c == '*' || c == '/' ||
                c == '.' || c == '=' || c == '\r';
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

        if (isOperator(text)) {
            if (currentText.isEmpty() || isOperator(currentText.substring(currentText.length() - 1))) {
                return;
            }
        } else if (text.equals(".")) {
            if (hasDecimalPointInCurrentNumber(currentText)) {
                return;
            }
        }

        display.setText(currentText + text);
    }

    private boolean isOperator(String text) {
        return text.length() == 1 && isOperator(text.charAt(0));
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasDecimalPointInCurrentNumber(String text) {
        if (text.isEmpty()) return false;

        int lastOperatorIndex = -1;
        for (int i = text.length() - 1; i >= 0; i--) {
            if (isOperator(text.charAt(i))) {
                lastOperatorIndex = i;
                break;
            }
        }

        String currentNumber = lastOperatorIndex == -1 ? text : text.substring(lastOperatorIndex + 1);
        return currentNumber.contains(".");
    }

    private void clear() {
        display.setText("0");
        calculator.clear();
        startNewNumber = true;
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
            display.setText("Ошибка: " + e.getMessage());
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
            String formatted = String.format("%.10f", result);
            return trimTrailingZeros(formatted);
        }
    }

    private String trimTrailingZeros(String number) {
        if (!number.contains(".")) return number;

        int i = number.length() - 1;
        while (i >= 0 && number.charAt(i) == '0') {
            i--;
        }

        if (i >= 0 && number.charAt(i) == '.') {
            i--;
        }

        return number.substring(0, i + 1);
    }
}