package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.dto.OrderItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormModel {
    public static List<OrderItemDto> getItemDetails(String orderId) throws SQLException, ClassNotFoundException {
        List<OrderItemDto> orderItemDto = new ArrayList<>();

        String sql = "SELECT\n" +
                "    moi.MenuItemID,\n" +
                "    SUM(moi.Qty) AS TotalQty,\n" +
                "    mi.Name,\n" +
                "    mi.Price\n" +
                "FROM\n" +
                "    menuorderitem moi\n" +
                "JOIN\n" +
                "    menuitem mi ON moi.MenuItemID = mi.MenuItemID\n" +
                "WHERE\n" +
                "    moi.OrderID = ?\n" +
                "GROUP BY\n" +
                "    moi.MenuItemID, mi.Name, mi.Price;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, orderId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String menuItemID = resultSet.getString("MenuItemID");
                String itemName = resultSet.getString("Name");
                int totalQty = resultSet.getInt("TotalQty");
                double price = Double.parseDouble(resultSet.getString("Price"));

                OrderItemDto orderItem = new OrderItemDto(menuItemID, itemName, totalQty, price);
                orderItemDto.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderItemDto;
    }

}
