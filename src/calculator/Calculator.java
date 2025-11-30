package calculator;

public class Calculator {

    public double evaluate(String expression) {
        try {
            return evalSimpleExpression(expression);
        } catch (Exception e) {
            throw new ArithmeticException("Некорректное выражение");
        }
    }

    private double evalSimpleExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");

        if (expression.matches("-?\\d+(\\.\\d+)?")) {
            return Double.parseDouble(expression);
        }

        // Обработка операций с учетом приоритета
        int parenCount = 0;
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == ')') parenCount++;
            else if (c == '(') parenCount--;

            if (parenCount == 0 && (c == '+' || c == '-') && i > 0) {
                double left = evalSimpleExpression(expression.substring(0, i));
                double right = evalSimpleExpression(expression.substring(i + 1));
                return c == '+' ? left + right : left - right;
            }
        }

        parenCount = 0;
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == ')') parenCount++;
            else if (c == '(') parenCount--;

            if (parenCount == 0 && (c == '*' || c == '/') && i > 0) {
                double left = evalSimpleExpression(expression.substring(0, i));
                double right = evalSimpleExpression(expression.substring(i + 1));
                if (c == '*') {
                    return left * right;
                } else {
                    if (right == 0) {
                        throw new ArithmeticException("Деление на ноль");
                    }
                    return left / right;
                }
            }
        }

        if (expression.startsWith("(") && expression.endsWith(")")) {
            return evalSimpleExpression(expression.substring(1, expression.length() - 1));
        }

        return Double.parseDouble(expression);
    }

    public void clear() {
        // Сброс состояния калькулятора
    }
}