package edu.ijse.datadish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class InventoryDto {
    private String id;
    private String name;
    private int qty;
    private int stockLevel;
}
