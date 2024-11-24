package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.LogInDto;
import edu.ijse.datadish.util.Password;
import edu.ijse.datadish.util.Refarance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInModel {

    public boolean cheakLogin(LogInDto logInDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            String userQuery = "SELECT * FROM user WHERE UserName = ?";
            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(1, logInDto.getUserName());

            System.out.println("Checking user credentials...");

            ResultSet userResult = userStatement.executeQuery();

            if (!userResult.next()) {
                connection.rollback();
                return false; 
            }

            String hashedPassword = userResult.getString("Password");

            if (!Password.checkPassword(logInDto.getPassword(), hashedPassword)) {
                connection.rollback();
                return false;
            }

            String role = userResult.getString("Role");
            String email = userResult.getString("Email");
            logInDto.setRole(role);
            logInDto.setEmail(email);

            System.out.println("User credentials verified.");
            System.out.println("Role: " + role + ", Email: " + email);

            String empIdQuery = "SELECT employee.EmployeeID FROM employee WHERE UserName = ?";
            PreparedStatement empStatement = connection.prepareStatement(empIdQuery);
            empStatement.setString(1, logInDto.getUserName());

            System.out.println("Fetching Employee ID...");

            ResultSet empResult = empStatement.executeQuery();

            if (empResult.next()) {
                String employeeId = empResult.getString("EmployeeID");
                Refarance.employeeID = employeeId;
                System.out.println("Employee ID: " + employeeId);
            } else {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String query = "SELECT Email FROM user WHERE Email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean updatePassword(String email, String newPassword) throws SQLException, ClassNotFoundException {
        String query = "UPDATE user SET Password = ? WHERE Email = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, newPassword);
        statement.setString(2, email);

        return statement.executeUpdate() > 0;
    }

}

//    public boolean cheakLogin(LogInDto logInDto) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "SELECT * FROM user WHERE UserName = ? AND Password = ?";
//        String empId = "Select employee.EmployeeID from employee where UserName = ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//
//        statement.setString(1,logInDto.getUserName() );
//        statement.setString(2, logInDto.getPassword());
//
//        System.out.println("initialized");
//
//        ResultSet result = statement.executeQuery();
//
//        System.out.println("got result set");
//
//        if (!result.next()) {
//            return false;
//        }
//
//        String role = result.getString("Role");
//        String email = result.getString("Email");
//        logInDto.setEmail(email);
//        logInDto.setRole(role);
//
//        System.out.println(logInDto.getUserName());
//        System.out.println(logInDto.getPassword());
//        System.out.println(logInDto.getRole());
//        System.out.println(logInDto.getEmail());
//
//        return true;
//    }

