package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEmployeeModel {

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET Address = ? , Contact = ?, Status = ? , Email = ? WHERE EmployeeID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, employeeDto.getAddress());
        statement.setString(2, employeeDto.getEmployeeContact());
        statement.setString(3, employeeDto.getEmployeeStatus());
        statement.setString(4, employeeDto.getEmail());
        statement.setString(5, employeeDto.getEmployeeID());


        int result = statement.executeUpdate();

        return result > 0;
    }

    public String getEmployeeEmail(String employeeID) throws SQLException, ClassNotFoundException {
        String sql = "select Email from employee where EmployeeID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, employeeID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "null";
    }
}
