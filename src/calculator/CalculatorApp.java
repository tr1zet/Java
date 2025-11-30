package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/calculator/calculator.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Калькулятор");
            Scene scene = new Scene(root, 400, 500);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(400);
            primaryStage.setMinHeight(500);
            primaryStage.show();

        } catch (Exception e) {
            // В production коде не показываем stack trace
            System.err.println("Не удалось запустить калькулятор");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}