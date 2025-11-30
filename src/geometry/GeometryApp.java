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
            // Правильный путь к FXML с учетом структуры
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/geometry/geometry.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Геометрические фигуры");
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading geometry.fxml: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}