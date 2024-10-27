package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddFoodItemModel {
    public boolean addItem(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO MenuItems (MenuItemID, Name, Description, Price, Category, Availability) VALUES (?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, foodDto.getFoodId());
        statement.setString(2, foodDto.getFoodName());
        statement.setString(3, foodDto.getFoodDescription());
        statement.setDouble(4, foodDto.getFoodPrice());
        statement.setString(5, foodDto.getFoodCategory());
        statement.setString(6, foodDto.getFoodAvailability());

        System.out.println("initialized");

        ResultSet result = statement.executeQuery();

        System.out.println("got result set");

        if (!result.next()) {
            return false;
        }

        return true;
    }

    public static String generateNextID() {
        String nextID = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT MenuItemID FROM MenuItem ORDER BY MenuItemID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("MenuItemID");
                int number = Integer.parseInt(lastID);
                nextID = String.format("M%03d", number + 1);
            } else {
                nextID = "M001";
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextID;
    }

}
