package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.Map;

public class CheckoutFormController {

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @Setter
    private Map<FoodDto, Integer> cartItems;

    @FXML
    private void submitCheckout() {
        String customerName = nameField.getText();
        String password = passwordField.getText();

        if (customerName.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Required");
            alert.setHeaderText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Checkout Successful");
        successAlert.setHeaderText("Order has been placed successfully!");
        successAlert.showAndWait();

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
