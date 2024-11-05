package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.InventoryDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryModel {
    public ObservableList<InventoryDto> getAllInventoryItems() throws SQLException, ClassNotFoundException {
        ObservableList<InventoryDto> itemView = FXCollections.observableArrayList();

        String sql = "SELECT * FROM inventory";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString("InventoryID");
            String name = resultSet.getString("ItemName");
            int qty = resultSet.getInt("Qty");
            int stockLevel = resultSet.getInt("StockLevel");

            InventoryDto inventoryDto = new InventoryDto(id, name, qty, stockLevel);
            itemView.add(inventoryDto);
        }
        return itemView;
    }
}
