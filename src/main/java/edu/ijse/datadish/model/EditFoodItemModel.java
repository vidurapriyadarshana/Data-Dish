package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditFoodItemModel {

    public FoodDto editFoodItem() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM menuitem";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No data found");
            alert.showAndWait();
            return null;
        }

        String id = result.getString("MenuItemID");
        String name = result.getString("Name");
        double price = result.getDouble("Price");
        String category = result.getString("Category");
        String availability = result.getString("Availability");
        String imagePath = result.getString("ImageData");

        FoodDto foodDto = new FoodDto(id, name, price, category, availability, imagePath);

        return foodDto;
    }

    public boolean editFoodItem(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE menuitem SET Name = ?, Price = ?, Category = ?, Availability = ?, ImageData = ? WHERE MenuItemID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, foodDto.getFoodName());
        statement.setDouble(2, foodDto.getFoodPrice());
        statement.setString(3, foodDto.getFoodCategory());
        statement.setString(4, foodDto.getFoodAvailability());
        statement.setString(5, foodDto.getFoodImagePath());
        statement.setString(6, foodDto.getFoodId());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;
    }

}
