package geometry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GeometryApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/geometry/geometry.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Геометрические фигуры");
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Ошибка загрузки приложения: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}