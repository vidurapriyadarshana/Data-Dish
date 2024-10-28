package edu.ijse.datadish.dto;

import javafx.scene.image.Image;
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
    private double foodPrice;
    private String foodCategory;
    private String foodAvailability;
    private Image foodImage;
}
