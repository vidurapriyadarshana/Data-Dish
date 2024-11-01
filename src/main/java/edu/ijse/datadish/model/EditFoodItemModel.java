package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import java.sql.*;

import static edu.ijse.datadish.model.AddFoodItemModel.getFileExtension;

public class EditFoodItemModel {

    private static final String PROFILE_IMAGES_DIR = "src/main/resources/assests/food/";
    private FoodDto foodDto = new FoodDto();
    static {
        try {
            Files.createDirectories(Paths.get(PROFILE_IMAGES_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String saveImage(File sourceFile, String itemName) throws IOException {
        String fileExtension = getFileExtension(sourceFile.getName());
        String uniqueFilename = itemName + "_" + System.currentTimeMillis() + fileExtension;
        Path destinationPath = Paths.get(PROFILE_IMAGES_DIR, uniqueFilename);
        Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        return destinationPath.toString();
    }

    public boolean updateFoodItem(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE menuitem SET Name = ?, Price = ?, Category = ?, Availability = ?, ImageData = ? WHERE MenuItemID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, foodDto.getFoodName());
        statement.setDouble(2, foodDto.getFoodPrice());
        statement.setString(3, foodDto.getFoodCategory());
        statement.setString(4, foodDto.getFoodAvailability());
        statement.setString(5, foodDto.getFoodImagePath());
        statement.setString(6, foodDto.getFoodId());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;
    }

    public boolean saveImagePath(String foodId , String imagePath) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE menuitem SET ImageData = ? WHERE MenuItemID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, imagePath);
        statement.setString(2, foodId);

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;
    }

    public String getImagePath(String itemId) throws SQLException, ClassNotFoundException {
        String query = "SELECT ImageData FROM menuItem WHERE MenuItemID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, itemId);

        ResultSet resultSet = preparedStatement.executeQuery();

        String imagePath = null;
        if (resultSet.next()) { // Move to the first row
            imagePath = resultSet.getString("ImageData");
            foodDto.setFoodImagePath(imagePath); // Assuming foodDto is properly initialized
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return imagePath;
    }

}