package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeSalaryModel {
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

            String query = "SELECT SalaryID FROM salary ORDER BY SalaryID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("SalaryID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("S%03d", number + 1);
                System.out.println("New ID generated: " + nextID);
            } else {
                nextID = "S001";
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

    public static List<String> getEmployeeNames() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Name FROM employee";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        List<String> employeeNames = new ArrayList<>();

        while (resultSet.next()) {
            employeeNames.add(resultSet.getString("Name"));
        }

        return employeeNames;
    }

}
