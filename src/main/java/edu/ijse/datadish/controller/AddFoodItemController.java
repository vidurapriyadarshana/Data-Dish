package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.AddFoodItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class AddFoodItemController {

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
        foodDto.setFoodId(lblId.getText());
        foodDto.setFoodName(txtName.getText());
        foodDto.setFoodDescription(txtDesc.getText());
        foodDto.setFoodPrice(Double.parseDouble(txtPrice.getText()));
        foodDto.setFoodCategory(txtCategory.getText());
        foodDto.setFoodAvailability("Available");
        foodDto.setFoodImage(imageView.getImage());

        System.out.println("Food Item Added: " + foodDto.getFoodName());

        addFoodItemModel.addItem(foodDto);

        System.out.println("Food Item Added: " + foodDto.getFoodName());

        Stage stage = (Stage) mainAnchor.getScene().getWindow();
        stage.close();

    }

    @FXML
    void chooseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(mainAnchor.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(((File) selectedFile).toURI().toString());
            imageView.setImage(image);
            foodDto.setFoodImage(image);
        }
    }

}
