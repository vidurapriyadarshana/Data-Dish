package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.dto.OrderDto;

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

    public static String generateNextOrderID() {
        String nextID = "O001"; // Default ID if no previous entries are found.

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1"
             );
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("OrderID");
                if (lastID != null && !lastID.isEmpty()) {
                    int number = Integer.parseInt(lastID.substring(1));
                    nextID = String.format("O%03d", number + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return nextID;
    }

    public static boolean saveOrder(OrderDto order) {
        String sql = "INSERT INTO orders (OrderID, CustomerID, EmployeeID, TableID, OrderDate, TotalAmount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, order.getOrderId());
            statement.setString(2, order.getCustomerId());
            statement.setString(3, order.getEmployeeId());
            statement.setString(4, order.getTableId());
            statement.setString(5, order.getOrderDate());
            statement.setDouble(6, Double.parseDouble(order.getTotalAmount()));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to save order: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static String generateNextCustomerID() {
        String nextID = "C001"; // Default ID if no previous entries are found.

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT CustomerID FROM customer ORDER BY CustomerID DESC LIMIT 1"
             );
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("CustomerID");
                if (lastID != null && !lastID.isEmpty()) {
                    int number = Integer.parseInt(lastID.substring(1));
                    nextID = String.format("C%03d", number + 1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return nextID;
    }
}
