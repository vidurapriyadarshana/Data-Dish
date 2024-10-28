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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return foodItems;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM menuitem WHERE MenuItemID = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateFoodAvailability(String id, String availability) {
        String sql = "UPDATE menuitem SET Availability = ? WHERE MenuItemID = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, availability);
            statement.setString(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
