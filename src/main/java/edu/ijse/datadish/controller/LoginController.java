package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.LogInDto;
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
    void LogInAction(ActionEvent event) {
        System.out.println("button clicked");
        String userName = txtUserName.getText();
        String password = txtpassword.getText();

        logInDto.setUserName(userName);
        logInDto.setPassword(password);

        try {
            boolean isLoggedIn = logInModel.cheakLogin(logInDto);
            String role = logInDto.getRole();
            if (isLoggedIn && role.equals("Admin")) {
                System.out.println("Successful Admin");
                mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/AdminDash.fxml")));
            }else if(isLoggedIn && role.equals("Employee")){
                System.out.println("Successful Employee");
            }else {
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
