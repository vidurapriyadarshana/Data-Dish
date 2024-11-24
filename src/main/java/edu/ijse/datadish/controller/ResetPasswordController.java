package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.LogInModel;
import edu.ijse.datadish.util.Refarance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    private String email;

    private LogInModel logInModel = new LogInModel();

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) txtNewPassword.getScene().getWindow();
        stage.close();
    }

    @FXML
    void resetPassword(ActionEvent event) {
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Fields cannot be empty!").show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
            return;
        }

        try {
            boolean isUpdated = logInModel.updatePassword(email, newPassword);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Password reset successfully!").show();
                Stage stage = (Stage) txtNewPassword.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to reset password!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.email = Refarance.email;
    }
}
