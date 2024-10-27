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
        System.out.println("Connected to the database.");

        String sql = "INSERT INTO MenuItem (MenuItemID, Name, Description, Price, Category, Availability) VALUES (?,?,?,?,?,?)";
        System.out.println("Query prepared.");

        PreparedStatement statement = connection.prepareStatement(sql);
        System.out.println("Statement initialized.");

        statement.setString(1, foodDto.getFoodId());
        statement.setString(2, foodDto.getFoodName());
        statement.setString(3, foodDto.getFoodDescription());
        statement.setDouble(4, foodDto.getFoodPrice());
        statement.setString(5, foodDto.getFoodCategory());
        statement.setString(6, foodDto.getFoodAvailability());

        System.out.println("Parameters set.");

        int rowsAffected = statement.executeUpdate();

        System.out.println("Insert operation executed.");

        return rowsAffected > 0;
    }


    public static String generateNextID() {
        String nextID = null;

        try {
            System.out.println("Generating ID...");
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println("Connected to database.");

            String query = "SELECT MenuItemID FROM MenuItem ORDER BY MenuItemID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("MenuItemID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("M%03d", number + 1);
                System.out.println("New ID generated: " + nextID);
            } else {
                nextID = "M001";
                System.out.println("No entries found, starting with ID: " + nextID);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextID;
    }

}
