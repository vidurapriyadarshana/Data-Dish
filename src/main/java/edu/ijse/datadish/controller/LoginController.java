package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.LogInDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import edu.ijse.datadish.model.LogInModel;

import java.security.spec.RSAOtherPrimeInfo;

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
    void LogInAction(ActionEvent event) {
        System.out.println("button clicked");
        String userName = txtUserName.getText();
        String password = txtpassword.getText();

        logInDto.setUserName(userName);
        logInDto.setPassword(password);

        try {
            boolean isLoggedIn = logInModel.cheakLogin(logInDto);
            if (isLoggedIn) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                System.out.println("Successful");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                System.out.println("Unsuccessful");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error" + e.getMessage());
        }
    }

    @FXML
    void forgotPassAction(MouseEvent event) {

    }

}
