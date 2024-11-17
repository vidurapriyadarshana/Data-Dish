package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.dto.OrderDto;
import edu.ijse.datadish.dto.OrderItemDto;

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
        String nextID = "O001";

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

    public static boolean saveOrder(List<OrderItemDto> orderItems, OrderDto order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer (CustomerID, Name, Contact) VALUES (?,?,?) ";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, order.getCustomerId());
        preparedStatement.setString(2, order.getCustomerName());
        preparedStatement.setString(3, order.getCustomerContact());

        int rowsInserted = preparedStatement.executeUpdate();

        if (rowsInserted > 0) {
            return true;
        }
        
        return false;
    }

    public static String generateNextCustomerID() {
        String nextID = "C001";

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
