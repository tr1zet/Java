package geometry;

import common.AppLauncher;
import javafx.application.Application;
import javafx.stage.Stage;

public class GeometryApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppLauncher.launchApp(primaryStage,
                "/geometry/geometry.fxml",
                "Геометрические фигуры",
                800, 600, 600, 400
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}