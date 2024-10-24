package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.LogInDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInModel {

    public boolean cheakLogin(LogInDto logInDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE UserName = ? AND Password = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1,logInDto.getUserName() );
        statement.setString(2, logInDto.getPassword());

        System.out.println("initialized");

        ResultSet result = statement.executeQuery();

        System.out.println("got result set");

        if (!result.next()) {
            return false;
        }
        String role = result.getString("Role");
        logInDto.setRole(role);
        logInDto.setEmail(result.getString("Email"));
        return true;
    }
}
