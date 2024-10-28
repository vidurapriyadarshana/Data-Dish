package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddItemModel {
    public ObservableList<FoodDto> loadTable() {
        ObservableList<FoodDto> foodItems = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT MenuItemID, Name, Price, Category, Availability FROM MenuItem";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("MenuItemID");
                String name = resultSet.getString("Name");
                double price = resultSet.getDouble("Price");
                String category = resultSet.getString("Category");
                String availability = resultSet.getString("Availability");

                FoodDto foodDto = new FoodDto(id, name, price, category, availability, null);
                foodItems.add(foodDto);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return foodItems;
    }
}
