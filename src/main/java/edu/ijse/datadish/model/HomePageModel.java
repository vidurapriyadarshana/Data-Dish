package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.dto.OrderDto;
import edu.ijse.datadish.dto.OrderItemDto;

import java.sql.*;
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


    public static boolean saveOrder(List<OrderItemDto> orderItemsDto, OrderDto orderDto) throws SQLException, ClassNotFoundException {
        String customer = "INSERT INTO customer (CustomerID, Name, Contact) VALUES (?,?,?)";
        String orders = "INSERT INTO orders (OrderID, CustomerID, TableID, Date, TotalAmount, EmployeeID) VALUES (?,?,?,?,?,?)";
        String menuOrderItem = "INSERT INTO menuorderitem(MenuItemID, OrderID, Qty) VALUES (?,?,?)";

        Connection connection = null;
        PreparedStatement customerStatement = null;
        PreparedStatement ordersStatement = null;
        PreparedStatement menuOrderItemStatement = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); 

            customerStatement = connection.prepareStatement(customer);
            customerStatement.setString(1, orderDto.getCustomerId());
            customerStatement.setString(2, orderDto.getCustomerName());
            customerStatement.setString(3, orderDto.getCustomerContact());
            int customerRowsInserted = customerStatement.executeUpdate();

            if (customerRowsInserted <= 0) {
                connection.rollback();
                return false;
            }

            ordersStatement = connection.prepareStatement(orders);
            ordersStatement.setString(1, orderDto.getOrderId());
            ordersStatement.setString(2, orderDto.getCustomerId());
            ordersStatement.setString(3, orderDto.getTableId());
            ordersStatement.setDate(4, Date.valueOf(orderDto.getOrderDate()));
            ordersStatement.setString(5, orderDto.getTotalAmount());
            ordersStatement.setString(6, orderDto.getEmployeeId());
            int orderRowsInserted = ordersStatement.executeUpdate();

            if (orderRowsInserted <= 0) {
                connection.rollback();
                return false;
            }

            menuOrderItemStatement = connection.prepareStatement(menuOrderItem);
            for (OrderItemDto item : orderItemsDto) {
                menuOrderItemStatement.setString(1, item.getFoodId());
                menuOrderItemStatement.setString(2, orderDto.getOrderId());
                menuOrderItemStatement.setInt(3, item.getQuantity());
                int itemRowsInserted = menuOrderItemStatement.executeUpdate();

                if (itemRowsInserted <= 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (customerStatement != null) customerStatement.close();
            if (ordersStatement != null) ordersStatement.close();
            if (menuOrderItemStatement != null) menuOrderItemStatement.close();
            if (connection != null) connection.setAutoCommit(true);
        }
    }


//    public static boolean saveOrder(List<OrderItemDto> orderItems, OrderDto order) throws SQLException, ClassNotFoundException {
//        String customer = "INSERT INTO customer (CustomerID, Name, Contact) VALUES (?,?,?) ";
//        String orders = "INSERT INTO orders VALUES (?,?,?,?,?,?)";
//        String menuOrderItem = " INSERT INTO menuorderitem(MenuItemID,OrderID,Qty) VALUES (?,?,?)";
//
//
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(customer);
//
//        preparedStatement.setString(1, order.getCustomerId());
//        preparedStatement.setString(2, order.getCustomerName());
//        preparedStatement.setString(3, order.getCustomerContact());
//
//        int rowsInserted = preparedStatement.executeUpdate();
//
//        if (rowsInserted > 0) {
//            return true;
//        }
//
//        return false;
//    }

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
