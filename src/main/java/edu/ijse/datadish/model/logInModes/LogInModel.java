package edu.ijse.datadish.model.logInModes;

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
        String email = result.getString("Email");
        logInDto.setEmail(email);
        logInDto.setRole(role);

        System.out.println(logInDto.getUserName());
        System.out.println(logInDto.getPassword());
        System.out.println(logInDto.getRole());
        System.out.println(logInDto.getEmail());

        return true;
    }
}
