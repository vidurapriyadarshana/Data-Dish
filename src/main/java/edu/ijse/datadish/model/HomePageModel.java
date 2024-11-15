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

    public static String generateNextID() {
        String nextID = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("OrderID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("O%03d", number + 1);
            } else {
                nextID = "O001";
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return nextID;
    }


}
