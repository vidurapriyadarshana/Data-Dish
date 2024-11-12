package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePageModel {

    public static List<FoodDto> getAllMenuItems() {
        List<FoodDto> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM menuitem";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                FoodDto foodItem = new FoodDto(
                        resultSet.getString("MenuItemID"),
                        resultSet.getString("Name"),
                        resultSet.getDouble("Price"),
                        resultSet.getString("Category"),
                        resultSet.getString("Availability"),
                        resultSet.getString("ImageData")
                );
                foodItems.add(foodItem);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return foodItems;
    }
}
