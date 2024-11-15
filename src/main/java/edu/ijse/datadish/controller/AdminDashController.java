package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.LogInDto;
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

public class AdminDashController implements Initializable {

    @FXML
    private Button btnAddItem,btnResavtion,btnEmployee,btnHome,btnInventory,btnLogOut,btnReports;

    @FXML
    private AnchorPane loadPageAnchor,mainAnchor,menuAnchor;

    @FXML
    private Label lblSetName;

    @FXML
    private Label lblSetRole;

    private String employeeUserName;
    private String employeeRole;

    @FXML
    private Button btOrders;

    @FXML
    void navigateToAddItemPage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/AddItem.fxml")));
    }


    @FXML
    void navigateToEmployeePage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/EmployeeView.fxml")));
    }

    @FXML
    void navigateToHomePage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void navigateToInventoryPage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/InventoryView.fxml")));
    }

    @FXML
    void navigateToLogInPage(ActionEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Login.fxml")));
    }

    @FXML
    void navigateToReportsPage(ActionEvent event) {

    }

    @FXML
    void navigateToResavtionPage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TableView.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.employeeUserName = Refarance.employeeUserName;
        this.employeeRole = Refarance.employeeRole;

        lblSetName.setText(employeeUserName);
        lblSetRole.setText(employeeRole);
    }

    @FXML
    void navigateToOrdersPage(ActionEvent event) throws IOException {
        loadPageAnchor.getChildren().clear();
        loadPageAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CheckoutView.fxml")));
    }
}
