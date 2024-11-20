package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddTableModel {

    public static String generateNextID() {
        String nextID = null;

        try {
            System.out.println("Generating ID...");
            Connection connection = DBConnection.getInstance().getConnection();

            if (connection == null) {
                System.out.println("Database connection failed.");
                return null;
            }

            System.out.println("Connected to database.");

            String query = "SELECT TableID FROM tableinfo ORDER BY TableID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("TableID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("T%03d", number + 1);
                System.out.println("New ID generated: " + nextID);
            } else {
                nextID = "T001";
                System.out.println("No entries found, starting with ID: " + nextID);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return nextID;
    }

    public static boolean addNewTable(String id, String capacity) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO tableinfo (TableID, Capacity , Status) VALUES (?, ? ,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, id);
        statement.setString(2, capacity);
        statement.setString(3, "Available");

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;
    }
}
