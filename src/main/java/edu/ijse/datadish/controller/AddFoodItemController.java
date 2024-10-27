package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.AddFoodItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class AddFoodItemController{

    @FXML
    private Button btAddItem;

    @FXML
    private Button btImageChoose;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lblId;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    private FoodDto foodDto = new FoodDto();
    private AddFoodItemModel addFoodItemModel = new AddFoodItemModel();

    @FXML
    void addItemAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        System.out.println("add item action");
        String id = AddFoodItemModel.generateNextID();
        System.out.println("id genarated" + id);
        lblId.setText(id);
        foodDto.setFoodId(id);
        foodDto.setFoodName(txtName.getText());
        foodDto.setFoodDescription(txtDesc.getText());
        foodDto.setFoodPrice(Double.parseDouble(txtPrice.getText()));
        foodDto.setFoodCategory(txtCategory.getText());
        foodDto.setFoodAvailability("Available");
        foodDto.setFoodImage(imageView.getImage());

        System.out.println("initialized");

        System.out.println("Food Item Added: " + foodDto.getFoodName());



        try {
            boolean isAdded = addFoodItemModel.addItem(foodDto);
            if (isAdded) {
                System.out.println("Successful");
                showAlert("Add Item", "Item Added Successfully");
            } else {
                System.out.println("Unsuccessful");
                showAlert("Add Item", "Item Added Unsuccessfully");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }

        System.out.println("Food Item Added: " + foodDto.getFoodName());

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        stage.close();

    }

    @FXML
    void chooseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(mainAnchor.getScene().getWindow());
        System.out.println("choose file");
        if (selectedFile != null) {
            Image image = new Image(((File) selectedFile).toURI().toString());
            imageView.setImage(image);
            System.out.println("image choosed");
            foodDto.setFoodImage(image);
            System.out.println("image set");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}