package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEmployeeModel {
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

            String query = "SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("EmployeeID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("E%03d", number + 1);
                System.out.println("New ID generated: " + nextID);
            } else {
                nextID = "E001";
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

    public static void addEmployee(EmployeeDto employeeDto) {

    }
}
