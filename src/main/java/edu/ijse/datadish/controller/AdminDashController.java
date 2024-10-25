package edu.ijse.datadish.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminDashController {

    @FXML
    private Button btnAddItem,btnResavtion,btnEmployee,btnHome,btnInventory,btnLogOut,btnReports;

    @FXML
    private AnchorPane loadPageAnchor,mainAnchor,menuAnchor;

    @FXML
    void navigateToAddItemPage(ActionEvent event) {

    }

    @FXML
    void navigateToEmployeePage(ActionEvent event) {

    }

    @FXML
    void navigateToHomePage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void navigateToInventoryPage(ActionEvent event) {

    }

    @FXML
    void navigateToLogInPage(ActionEvent event) {

    }

    @FXML
    void navigateToReportsPage(ActionEvent event) {

    }

    @FXML
    void navigateToResavtionPage(ActionEvent event) {

    }

}
