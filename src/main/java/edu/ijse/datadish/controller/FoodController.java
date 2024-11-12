package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Setter;

public class FoodController {

    @FXML
    private Label foodNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Button btAddToCart;

    @FXML
    private ImageView imageLoad;

    @FXML
    private Label foodCount;

    private FoodDto foodItem;
    @Setter
    private Runnable addToCartAction;

    public void setData(FoodDto foodDto) {
        this.foodItem = foodDto;
        foodNameLabel.setText(foodDto.getFoodName());
        priceLabel.setText("$" + String.format("%.2f", foodDto.getFoodPrice()));
        foodCount.setText("Qty: 1"); // Default quantity
        // Load the image
        if (foodDto.getFoodImagePath() != null && !foodDto.getFoodImagePath().isEmpty()) {
            imageLoad.setImage(new Image("file:" + foodDto.getFoodImagePath()));
        }
    }


    @FXML
    private void addToCartAction() {
        if (addToCartAction != null) {
            addToCartAction.run();
        }
    }
}
