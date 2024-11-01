package edu.ijse.datadish.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoadProgressController implements Initializable {

    @FXML
    private ImageView loadImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            loadImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/loading.gif"))));
        });
    }
}