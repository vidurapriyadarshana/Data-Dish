package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.DeleteFoodItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class DeleteFoodItemController {

    @FXML
    private Button btAddItem;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtFoodId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    private FoodDto foodDto;
    private DeleteFoodItemModel deleteFoodItemModel;

    @FXML
    void deleteItemAction(ActionEvent event) throws SQLException, ClassNotFoundException {

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
