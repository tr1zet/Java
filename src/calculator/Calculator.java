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
        if (expression == null || expression.trim().isEmpty()) {
            throw new ArithmeticException("Пустое выражение");
        }

        // Убираем пробелы - безопасная операция
        expression = expression.replace(" ", "");

        // Проверяем базовые случаи - просто число
        if (isSimpleNumber(expression)) {
            return Double.parseDouble(expression);
        }

        // Используем безопасный парсинг вместо рекурсивных regex
        return parseExpression(expression);
    }

    private boolean isSimpleNumber(String str) {
        if (str == null || str.isEmpty()) return false;

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double parseExpression(String expression) {
        expression = removeOuterParentheses(expression);

        // Ищем оператор с наименьшим приоритетом
        int operatorIndex = findLowestPriorityOperator(expression);

        if (operatorIndex != -1) {
            char operator = expression.charAt(operatorIndex);
            String leftPart = expression.substring(0, operatorIndex);
            String rightPart = expression.substring(operatorIndex + 1);

            double left = parseExpression(leftPart);
            double right = parseExpression(rightPart);

            switch (operator) {
                case '+': return left + right;
                case '-': return left - right;
                case '*': return left * right;
                case '/':
                    if (right == 0) {
                        throw new ArithmeticException("Деление на ноль");
                    }
                    return left / right;
                default:
                    throw new ArithmeticException("Неизвестный оператор: " + operator);
            }
        }


        if (isSimpleNumber(expression)) {
            return Double.parseDouble(expression);
        }

        throw new ArithmeticException("Некорректное выражение: " + expression);
    }

    private int findLowestPriorityOperator(String expression) {
        int parenCount = 0;

        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == ')') parenCount++;
            else if (c == '(') parenCount--;

            if (parenCount == 0 && (c == '+' || c == '-') && i > 0 && i < expression.length() - 1) {
                if (c == '-' && (i == 0 || isOperator(expression.charAt(i - 1)))) {
                    continue;
                }
                return i;
            }
        }

        parenCount = 0;
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == ')') parenCount++;
            else if (c == '(') parenCount--;

            if (parenCount == 0 && (c == '*' || c == '/') && i > 0 && i < expression.length() - 1) {
                return i;
            }
        }

        return -1; // Операторов не найдено
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private String removeOuterParentheses(String expression) {
        if (expression.startsWith("(") && expression.endsWith(")")) {
            // Проверяем, что скобки сбалансированы
            int count = 1;
            for (int i = 1; i < expression.length() - 1; i++) {
                char c = expression.charAt(i);
                if (c == '(') count++;
                else if (c == ')') count--;

                if (count == 0) {
                    // Не все выражение в скобках
                    return expression;
                }
            }
            if (count == 1) {
                // Все выражение в скобках
                return expression.substring(1, expression.length() - 1);
            }
        }
        return expression;
    }

    public void clear() {
        // Сброс состояния калькулятора
    }
}