package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.LogInModel;
import edu.ijse.datadish.util.Refarance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private TextField txtEmail;

    private LogInModel logInModel = new LogInModel();
    private int generatedOtp;

    private CustMailSenderController mailSenderController = new CustMailSenderController();

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void submitEmail(ActionEvent event) {
        String email = txtEmail.getText();


        if (email.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Email cannot be empty!").show();
            return;
        }

        try {
            boolean emailExists = logInModel.checkEmail(email);

            if (emailExists) {
                generatedOtp = generateOtp();

                Refarance.email = email;
                Refarance.emailSendOtp = generatedOtp;

                System.out.println(generatedOtp);
                System.out.println(email);

                mailSenderController.sendEmail(email, "Your OTP Code", "Your OTP is: " + generatedOtp);

                Stage currentStage = (Stage) txtEmail.getScene().getWindow();
                currentStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyOtp.fxml"));
                Parent load = loader.load();

                Stage addItemStage = new Stage();
                addItemStage.setTitle("Forgot Password");
                addItemStage.setScene(new Scene(load));
                addItemStage.initModality(Modality.NONE);
                addItemStage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Email does not exist in the database!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
        }
    }

    private int generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}


//package edu.ijse.datadish.controller;
//
//import edu.ijse.datadish.model.LogInModel;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//import java.util.Random;
//
//public class ForgotPasswordController {
//
//    @FXML
//    private TextField txtEmail;
//
//    private LogInModel logInModel = new LogInModel();
//    private int generatedOtp;
//
//    @FXML
//    void submitEmail(ActionEvent event) {
//        String email = txtEmail.getText();
//
//        if (email.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Email cannot be empty!").show();
//            return;
//        }
//
//        try {
//            boolean emailExists = logInModel.checkEmail(email);
//
//            if (emailExists) {
//                generatedOtp = generateOtp();
//                // Send OTP to email
//                CustMailSenderController mailSender = new CustMailSenderController();
//                mailSender.setCustEmail(email);
//                mailSender.sendEmailWithSendgrid("vidura200427@gmail.com", email, "Your OTP Code", "Your OTP is: " + generatedOtp);
//                new Alert(Alert.AlertType.INFORMATION, "OTP sent to your email!").show();
//
//                // Open OTP verification window
//                Stage currentStage = (Stage) txtEmail.getScene().getWindow();
//                currentStage.close();
//                // Load OTP verification UI here
//                FXMLLoader.load(getClass().getResource("/view/VerifyOtp.fxml"));
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Email does not exist in the database!").show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
//        }
//    }
//
//    private int generateOtp() {
//        Random random = new Random();
//        return 100000 + random.nextInt(900000); // Generate a 6-digit OTP
//    }
//}
