package edu.ijse.datadish.model;

import edu.ijse.datadish.dto.TableDto;
import edu.ijse.datadish.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableViewModel {

    public ObservableList<TableDto> getAllTables() {
        ObservableList<TableDto> tableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tableinfo";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("TableID");
                String status = resultSet.getString("Status");
                int capacity = resultSet.getInt("Capacity");

                TableDto table = new TableDto(id, status, capacity);
                tableList.add(table);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tableList;
    }

    public ObservableList<TableDto> getAvailableTables() {
        ObservableList<TableDto> tableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tableinfo WHERE Status = 'Available'";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("TableID");
                String status = resultSet.getString("Status");
                int capacity = resultSet.getInt("Capacity");

                TableDto table = new TableDto(id, status, capacity);
                tableList.add(table);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tableList;
    }

    public boolean deleteTable(String tableId) {
        String sql = "DELETE FROM tableinfo WHERE TableID = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tableId);
            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateTableStatus(String tableId, String status) {
        String sql = "UPDATE tableinfo SET Status = ? WHERE TableID = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setString(2, tableId);
            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
