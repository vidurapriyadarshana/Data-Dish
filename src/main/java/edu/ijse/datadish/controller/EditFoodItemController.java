package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.EditFoodItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditFoodItemController implements Initializable {

    @FXML
    private Button btAddItem;

    @FXML
    private CheckBox btAvailable;

    @FXML
    private Button btImageChange;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private AnchorPane mainAnchor;

    private FoodDto foodDto = new FoodDto();
    private EditFoodItemModel editFoodItemModel = new EditFoodItemModel();

    @FXML
    void changeImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(mainAnchor.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String imagePath = EditFoodItemModel.saveImage(selectedFile, txtName.getText());
                foodDto.setFoodImagePath(imagePath);

                boolean isAdded = editFoodItemModel.saveImagePath(foodDto.getFoodId(), imagePath);

                if (isAdded) {
                    showAlert("Edit Item", "Image Saved Successfully");
                } else {
                    showAlert("Edit Item", "Image Saved Unsuccessfully");
                }

                Image image = new Image("file:" + imagePath); // Load image from saved path
                imageView.setImage(image);
            } catch (IOException e) {
                showAlert("Error", "Error saving image: " + e.getMessage());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void editItemAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblId.getText();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String category = txtCategory.getText();
        String availability = btAvailable.isSelected() ? "Available" : "Not Available";

        foodDto.setFoodId(id);
        foodDto.setFoodName(name);
        foodDto.setFoodPrice(price);
        foodDto.setFoodCategory(category);
        foodDto.setFoodAvailability(availability);

        boolean isUpdated = editFoodItemModel.updateFoodItem(foodDto);
        if (isUpdated) {
            showAlert("Edit Item", "Item Edited Successfully");
        } else {
            showAlert("Edit Item", "Item Edited Unsuccessfully");
        }

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize method if needed
    }

    public void setFoodDto(FoodDto food) throws SQLException, ClassNotFoundException {
        this.foodDto = food;

        lblId.setText(food.getFoodId());
        txtName.setText(food.getFoodName());
        txtPrice.setText(String.valueOf(food.getFoodPrice()));
        txtCategory.setText(food.getFoodCategory());

        String imagePath = editFoodItemModel.getImagePath(food.getFoodId());

        if (imagePath != null) {
            try {
                Image image = new Image("file:" + imagePath);
                imageView.setImage(image);
            } catch (IllegalArgumentException e) {
                System.out.println("Image not found at path: " + imagePath);
                e.printStackTrace();
            }
        }

        availableAction();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void availableAction() {
        btAvailable.setSelected("Available".equals(foodDto.getFoodAvailability()));
    }
}
