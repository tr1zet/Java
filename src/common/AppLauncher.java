package common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher {

    public static void launchApp(Stage stage, String fxmlPath, String title,
                                 int width, int height, int minWidth, int minHeight) {
        try {
            FXMLLoader loader = new FXMLLoader(AppLauncher.class.getResource(fxmlPath));
            Parent root = loader.load();

            stage.setTitle(title);
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.show();

        } catch (Exception e) {
            System.err.println("Не удалось запустить приложение: " + title);
        }
    }
}