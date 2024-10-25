package edu.ijse.datadish.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminDashController {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnResavtion;

    @FXML
    private AnchorPane loadPageAnchor;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane menuAnchor;

    @FXML
    void navigateToHomePage(ActionEvent event){

    }

    @FXML
    void navigateToLogInPage(ActionEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LogIn.fxml")));
    }

    @FXML
    void navigateToReportsPage(ActionEvent event) {

    }

    @FXML
    void navigateToResavtionPage(ActionEvent event) {

    }

}
