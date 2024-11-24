package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.LogInDto;
import edu.ijse.datadish.util.Password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEmployeeModel {

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        String employeeSql = "INSERT INTO employee (EmployeeID, Name, Contact, HireDate, Status, Address, Email, UserName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String userSql = "INSERT INTO user (UserName, Password, Email, Role) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement employeeStatement = null;
        PreparedStatement userStatement = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Hash the password before saving
            String hashedPassword = Password.hashPassword(employeeDto.getPassword());

            // Save user details
            userStatement = connection.prepareStatement(userSql);
            userStatement.setString(1, employeeDto.getUserName());
            userStatement.setString(2, hashedPassword); // Store hashed password
            userStatement.setString(3, employeeDto.getEmail());
            userStatement.setString(4, employeeDto.getRole());
            userStatement.executeUpdate();

            // Save employee details
            employeeStatement = connection.prepareStatement(employeeSql);
            employeeStatement.setString(1, employeeDto.getEmployeeID());
            employeeStatement.setString(2, employeeDto.getEmployeeName());
            employeeStatement.setString(3, employeeDto.getEmployeeContact());
            employeeStatement.setString(4, employeeDto.getHireDate());
            employeeStatement.setString(5, employeeDto.getEmployeeStatus());
            employeeStatement.setString(6, employeeDto.getAddress());
            employeeStatement.setString(7, employeeDto.getEmail());
            employeeStatement.setString(8, employeeDto.getUserName());
            employeeStatement.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;

        } finally {
            if (employeeStatement != null) {
                employeeStatement.close();
            }
            if (userStatement != null) {
                userStatement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }


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

}