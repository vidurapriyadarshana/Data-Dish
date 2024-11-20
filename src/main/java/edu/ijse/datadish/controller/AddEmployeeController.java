
package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.LogInDto;
import edu.ijse.datadish.model.AddEmployeeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    private Tab btDetails;

    @FXML
    private Tab btLogInInfo;

    @FXML
    private Button btNextPage;

    @FXML
    private Button btSignUp;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblHireDate;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TabPane tabPane;

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

    @FXML
    private ChoiceBox<String> choiceBox;

    private EmployeeDto employeeDto = new EmployeeDto();
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
    void signUpOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        if(name.isEmpty() || contact.isEmpty() || address.isEmpty() || email.isEmpty() || choiceBox.getValue() == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        employeeDto.setEmployeeID(lblEmpId.getText());
        employeeDto.setEmployeeName(name);
        employeeDto.setEmployeeContact(contact);
        employeeDto.setHireDate(lblHireDate.getText());
        employeeDto.setAddress(address);
        employeeDto.setEmail(email);
        employeeDto.setRole(choiceBox.getValue());
        employeeDto.setEmployeeStatus("Active");

        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if(userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        if(password.length() != 8) {
            showAlert("Error", "Password must be at least 8 characters long.");
            return;
        }

        if(!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        employeeDto.setUserName(userName);
        employeeDto.setPassword(password);

        boolean isSaved = addEmployeeModel.saveEmployee(employeeDto);

        if(isSaved) {
            showAlert("Success", "Employee saved successfully.");
            Stage stage = (Stage) mainAnchor.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Error", "Employee saving failed.");
        }

    }


    @FXML
    void navigateToSignUp(ActionEvent event) {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmpId.setText(AddEmployeeModel.generateNextID());
        lblHireDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        choiceBox.getItems().addAll(roleChoice);

        btNextPage.setOnAction(event -> {
            int currentIndex = tabPane.getSelectionModel().getSelectedIndex();
            if (currentIndex < tabPane.getTabs().size() - 1) {
                tabPane.getSelectionModel().select(currentIndex + 1);
            }
        });

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
