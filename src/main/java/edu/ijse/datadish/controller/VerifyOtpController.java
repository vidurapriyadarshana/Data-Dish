package edu.ijse.datadish.controller;

import edu.ijse.datadish.util.Refarance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VerifyOtpController implements Initializable {

    @FXML
    private TextField txtOtp;

    private int refaranceOtp;

    @FXML
    void verifyOtp(ActionEvent event) throws IOException {
        int enteredOtp = Integer.parseInt(txtOtp.getText());

        System.out.println(enteredOtp);
        System.out.println(refaranceOtp);

        if (enteredOtp == refaranceOtp) {
            Stage currentStage = (Stage) txtOtp.getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPassword.fxml"));
            Parent load = loader.load();

            Stage addItemStage = new Stage();
            addItemStage.setTitle("Verify Otp");
            addItemStage.setScene(new Scene(load));
            addItemStage.initModality(Modality.NONE);
            addItemStage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.refaranceOtp = Refarance.emailSendOtp;
        System.out.println(refaranceOtp);
    }
}
