package edu.ijse.datadish.controller.addItemControllers;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.addItemModels.AddFoodItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddFoodItemController implements Initializable {

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
        String id = AddFoodItemModel.generateNextID();
        lblId.setText(id);

        foodDto.setFoodId(id);
        foodDto.setFoodName(txtName.getText());
        foodDto.setFoodPrice(Double.parseDouble(txtPrice.getText()));
        foodDto.setFoodCategory(txtCategory.getText());
        foodDto.setFoodAvailability("Available");

        try {
            boolean isAdded = addFoodItemModel.addItem(foodDto);
            if (isAdded) {
                showAlert("Add Item", "Item Added Successfully");
            } else {
                showAlert("Add Item", "Item Added Unsuccessfully");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        stage.close();
    }

    @FXML
    void chooseImageAction(ActionEvent event) {
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblId.setText(AddFoodItemModel.generateNextID());
    }
}
