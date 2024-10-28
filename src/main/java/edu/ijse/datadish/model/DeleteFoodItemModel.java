package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFoodItemModel {
    public boolean delete(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
