package edu.ijse.datadish.controller.addItemControllers;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.addItemModels.AddFoodItemModel;
import edu.ijse.datadish.model.addItemModels.EditFoodItemModel;
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
    private AddItemController addItemController = new AddItemController();


    @FXML
    void changeImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(mainAnchor.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String imagePath = AddFoodItemModel.saveImage(selectedFile, txtName.getText());
                foodDto.setFoodImagePath(imagePath);

                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                showAlert("Error", "Error saving image: " + e.getMessage());
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

        boolean isAdded = editFoodItemModel.editFoodItem(foodDto);
        if (isAdded) {
            showAlert("Edit Item", "Item Edited Successfully");
        } else {
            showAlert("Edit Item", "Item Edited Unsuccessfully");
        }

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            foodDto = editFoodItemModel.updateFoodItem();
            lblId.setText(foodDto.getFoodId());
            txtName.setText(foodDto.getFoodName());
            txtCategory.setText(foodDto.getFoodCategory());
            txtPrice.setText(String.valueOf(foodDto.getFoodPrice()));

            availableAction();

            btAvailable.selectedProperty().addListener((observable, oldValue, newValue) -> {
                foodDto.setFoodAvailability(newValue ? "Available" : "Not Available");
            });

        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Error loading food item: " + e.getMessage());
        }
    }

    public void setFoodDto(FoodDto food) {
        this.foodDto = food;
        lblId.setText(food.getFoodId());
        txtName.setText(food.getFoodName());
        txtCategory.setText(food.getFoodCategory());
        txtPrice.setText(String.valueOf(food.getFoodPrice()));

        // Update the checkbox based on the availability status.
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
        // Set the checkbox based on whether the food item is available.
        btAvailable.setSelected("Available".equals(foodDto.getFoodAvailability()));
    }

}