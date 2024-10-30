package edu.ijse.datadish.model.addItemModels;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.ijse.datadish.model.addItemModels.AddFoodItemModel.getFileExtension;

public class EditFoodItemModel {

    private static final String PROFILE_IMAGES_DIR = "src/main/resources/assests/food/";

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


    public FoodDto updateFoodItem() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM menuitem";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No data found");
            alert.showAndWait();
            return null;
        }

        String id = result.getString("MenuItemID");
        String name = result.getString("Name");
        double price = result.getDouble("Price");
        String category = result.getString("Category");
        String availability = result.getString("Availability");
        String imagePath = result.getString("ImageData");

        System.out.println(id);
        System.out.println(name);
        System.out.println(price);
        System.out.println(category);
        System.out.println(availability);
        System.out.println(imagePath);

        return new FoodDto(id, name, price, category, availability, imagePath);
    }


    public boolean editFoodItem(FoodDto foodDto) throws SQLException, ClassNotFoundException {
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

}