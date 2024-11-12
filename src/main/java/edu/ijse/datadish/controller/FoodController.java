package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FoodController {

    @FXML
    private Label foodName;

    @FXML
    private Label foodPrice;

    @FXML
    private ImageView imageLoad;

    @FXML
    void addToCartAction(ActionEvent event) {

    }

    public void setData(FoodDto foodDto) {
        foodName.setText(foodDto.getFoodName());
        foodPrice.setText(String.format("Price: LKR%.2f", foodDto.getFoodPrice()));

        String imagePath = foodDto.getFoodImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            imageLoad.setImage(new Image("file:" + imagePath));
        }
    }
}
