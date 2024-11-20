package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.LogInDto;
import edu.ijse.datadish.util.Refarance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import edu.ijse.datadish.model.LogInModel;

public class LoginController {

    @FXML
    private Button btnLogIn;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtpassword;

    private LogInModel logInModel = new LogInModel();
    private LogInDto logInDto = new LogInDto();

    @FXML
    public void initialize() {
        txtUserName.setOnAction(event -> txtpassword.requestFocus());
        txtpassword.setOnAction(this::LogInAction);
    }

    @FXML
    void LogInAction(ActionEvent event) {
        System.out.println("button clicked");
        String userName = txtUserName.getText();
        String password = txtpassword.getText();

        logInDto.setUserName(userName);
        logInDto.setPassword(password);


        try {
            boolean isLoggedIn = logInModel.cheakLogin(logInDto);

            Refarance.employeeUserName = userName;
            Refarance.employeeRole = logInDto.getRole();

            String role = logInDto.getRole();
            if (isLoggedIn && role.equals("Admin")) {
                System.out.println("Successful Admin");
                mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/AdminDash.fxml")));
            }else if(isLoggedIn && role.equals("Employee")){
                System.out.println("Successful Employee");
                mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/EmployeeDash.fxml")));
            }else {
                System.out.println("Unsuccessful");
                showAlert("Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void forgotPassAction(MouseEvent event) {

    }
}
