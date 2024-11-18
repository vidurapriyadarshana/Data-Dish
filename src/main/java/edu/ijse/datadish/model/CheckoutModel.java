package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.OrderTableDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutModel {

    public List<OrderTableDto> loadOrders() throws SQLException, ClassNotFoundException {
        List<OrderTableDto> orderList = new ArrayList<>();
        String sql = "SELECT o.TableID, o.OrderID, o.EmployeeID, o.TotalAmount FROM orders o JOIN menuorderitem moi ON o.OrderID = moi.OrderID WHERE moi.status = 'incomplete' GROUP BY o.OrderID";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                OrderTableDto order = new OrderTableDto();
                order.setTableId(resultSet.getString("TableID"));
                order.setOrderId(resultSet.getString("OrderID"));
                order.setEmployeeId(resultSet.getString("EmployeeID"));
                order.setTotalAmount(resultSet.getString("TotalAmount"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new SQLException("Error loading orders", e);
        }
        return orderList;
    }

    public void completeOrder(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE menuorderitem SET status = 'completed' WHERE OrderID = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error completing order", e);
        }
    }

}
