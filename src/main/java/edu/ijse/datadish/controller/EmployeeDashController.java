package edu.ijse.datadish.controller;

import edu.ijse.datadish.util.Refarance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDashController implements Initializable {

    @FXML
    private Button btOrders;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogOut;

    @FXML
    private Label lblSetName;

    @FXML
    private Label lblSetRole;

    @FXML
    private AnchorPane loadPageAnchor;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane menuAnchor;

    private String employeeUserName;
    private String employeeRole;

    @FXML
    void navigateToHomePage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void navigateToLogInPage(ActionEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Login.fxml")));
    }

    @FXML
    void navigateToOrdersPage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CheckoutView.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.employeeUserName = Refarance.employeeUserName;
        this.employeeRole = Refarance.employeeRole;

        lblSetName.setText(employeeUserName);
        lblSetRole.setText(employeeRole);
    }
}
