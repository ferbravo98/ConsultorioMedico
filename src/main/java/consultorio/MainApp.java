package consultorio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/PacientesView.fxml")
    );

    Scene scene = new Scene(loader.load(), 1200, 800);
    stage.setTitle("Consultorio Médico");
    stage.getIcons().add(
            new Image(getClass().getResourceAsStream("/img/logoApp.png"))
    );

    stage.setScene(scene);
    stage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}