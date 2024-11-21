package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeSalaryModel {
    public static String generateNextID() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            if (connection == null) {
                System.out.println("Database connection failed.");
                return "S001";
            }

            String query = "SELECT SalaryID FROM salary ORDER BY SalaryID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("SalaryID");
                int number = Integer.parseInt(lastID.substring(1));
                return String.format("S%03d", number + 1);
            } else {
                return "S001";
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return "S001";
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

    public static String getEmployeeId(String employeeName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT EmployeeID FROM employee WHERE Name = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employeeName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("EmployeeID");
        } else {
            return null;
        }
    }


    public static boolean addSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO salary (SalaryID, EmployeeID,Amount, PaymentDate) VALUES (?, ?, ?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, salaryDto.getSalaryId());
        statement.setString(2, salaryDto.getEmployeeId());
        statement.setDouble(3, salaryDto.getAmount());
        statement.setString(4, salaryDto.getDate());

        return statement.executeUpdate() > 0;
    }
}
