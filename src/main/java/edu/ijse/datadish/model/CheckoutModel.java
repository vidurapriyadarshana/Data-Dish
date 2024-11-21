package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.OrderDto;
import edu.ijse.datadish.dto.OrderTableDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckoutModel {

    public List<OrderTableDto> loadIncompleteOrders() throws Exception {
        String sql = "SELECT o.OrderID,o.TableID,o.TotalAmount,o.CustomerID,m.status FROM orders o JOIN menuorderitem m\n" +
                "ON o.OrderID = m.OrderID WHERE m.status = 'incomplete' GROUP BY o.OrderID ORDER BY o.OrderID;";
//        String sql = "SELECT o.OrderID, o.TableID, o.TotalAmount, o.CustomerID, m.status " +
//                "FROM orders o " +
//                "JOIN menuorderitem m ON o.OrderID = m.OrderID " +
//                "WHERE m.status = 'incomplete' " +
//                "ORDER BY m.OrderID;";

//        String sql = " SELECT o.OrderID, o.TableID, o.TotalAmount, o.CustomerID, m.status\n" +
//                "FROM orders o\n" +
//                "JOIN menuorderitem m ON o.OrderID = m.OrderID\n" +
//                "WHERE m.status = 'incomplete';";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        List<OrderTableDto> orderList = new ArrayList<>();

        while (resultSet.next()) {
            OrderTableDto order = new OrderTableDto();
            order.setOrderId(resultSet.getString("OrderID"));
            order.setTableId(resultSet.getString("TableID"));
            order.setTotalAmount(resultSet.getString("TotalAmount"));
            order.setEmployeeId(resultSet.getString("CustomerID"));
            order.setStatus(resultSet.getString("status"));

            orderList.add(order);
        }

        return orderList;
    }
}
