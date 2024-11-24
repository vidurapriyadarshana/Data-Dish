package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.InventoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddInventoryItemModel {

    public static boolean addItem(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO inventory (InventoryID, ItemName, Qty, StockLevel) VALUES (?,?,?,?);";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, inventoryDto.getId());
        statement.setString(2, inventoryDto.getName());
        statement.setInt(3, inventoryDto.getQty());
        statement.setInt(4, inventoryDto.getStockLevel());

        return statement.executeUpdate() > 0;
    }

    public static String generateNextID() {
        String nextID = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT InventoryID FROM inventory ORDER BY InventoryID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("InventoryID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("I%03d", number + 1);
            } else {
                nextID = "INV001";
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return nextID;
    }

}
