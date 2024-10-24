package edu.ijse.datadish;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Appinitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        stage.setScene(new Scene(load));
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assests/logo.png")));
        stage.getIcons().add(icon);
        stage.setTitle("DataDish");
        stage.show();
    }
}
