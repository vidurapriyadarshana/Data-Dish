package edu.ijse.datadish.dto;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class FoodDto {
    private String foodId;
    private String foodName;
    private String foodDescription;
    private double foodPrice;
    private String foodCategory;
    private String foodAvailability;
    private Image foodImage;
}
