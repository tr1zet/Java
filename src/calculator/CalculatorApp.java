package calculator;

import common.AppLauncher;
import javafx.application.Application;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppLauncher.launchApp(primaryStage,
                "/calculator/calculator.fxml",
                "Калькулятор",
                400, 500, 400, 500
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}