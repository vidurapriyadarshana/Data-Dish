package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.LogInDto;
import edu.ijse.datadish.model.AddEmployeeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {


    @FXML
    private Button btAddEmployee;

    @FXML
    private ChoiceBox<String> choiceBox;

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
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    private EmployeeDto employeeDto = new EmployeeDto();
    private LogInDto logInDto = new LogInDto();
    private AddEmployeeModel addEmployeeModel = new AddEmployeeModel();

    private String[] roleChoice = {"Admin","Employee"};
//    @FXML
//    public void initialize() {
//        txtName.setOnAction(event -> txtContact.requestFocus());
//        txtName.setOnAction(event -> txtAddress.requestFocus());
//        txtAddress.setOnAction(event -> txtUserName.requestFocus());
//        txtUserName.setOnAction(event -> txtPassword.requestFocus());
//        txtPassword.setOnAction(event -> txtConfirmPassword.requestFocus());
//        txtConfirmPassword.setOnAction(this::addEmployeeAction);
//
//    }


    @FXML
    void addEmployeeAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        addEmployeeDto(event);
        addEmployeeModel.addEmployee(employeeDto, logInDto);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmpId.setText(AddEmployeeModel.generateNextID());
        lblHireDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        choiceBox.getItems().addAll(roleChoice);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addEmployeeDto(ActionEvent event) {
        employeeDto.setEmployeeID(lblEmpId.getText());
        employeeDto.setEmployeeName(txtName.getText());
        employeeDto.setEmployeeContact(txtContact.getText());
        employeeDto.setHireDate(lblHireDate.getText());
        employeeDto.setUserName(txtUserName.getText());
        employeeDto.setEmployeeStatus("Active");
        employeeDto.setAddress(txtAddress.getText());

        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match");
        }else {
            logInDto.setUserName(txtUserName.getText());
            logInDto.setPassword(txtPassword.getText());
            logInDto.setEmail(txtEmail.getText());
            logInDto.setRole(choiceBox.getValue());
        }
    }
}