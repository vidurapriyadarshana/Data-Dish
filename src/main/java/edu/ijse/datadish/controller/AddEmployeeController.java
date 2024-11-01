package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.AddEmployeeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {


    @FXML
    private Button btAddEmployee;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblHireDate;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblHireTime;

    @FXML
    void addEmployeeAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmpId.setText(AddEmployeeModel.generateNextID());
        lblHireDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lblHireTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}