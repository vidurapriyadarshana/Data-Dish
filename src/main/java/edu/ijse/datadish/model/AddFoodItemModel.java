package edu.ijse.datadish.model;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.FoodDto;

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

public class AddFoodItemModel {

    private static final String IMAGES_DIR = "src/main/resources/assets/food/";

    static {
        try {
            Files.createDirectories(Paths.get(IMAGES_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addItem(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO MenuItem (MenuItemID, Name, Price, Category, Availability, ImageData) VALUES (?,?,?,?,?,?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, foodDto.getFoodId());
            statement.setString(2, foodDto.getFoodName());
            statement.setDouble(3, foodDto.getFoodPrice());
            statement.setString(4, foodDto.getFoodCategory());
            statement.setString(5, foodDto.getFoodAvailability());
            statement.setString(6, foodDto.getFoodImagePath());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static String generateNextID() {
        String nextID = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String query = "SELECT MenuItemID FROM MenuItem ORDER BY MenuItemID DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String lastID = resultSet.getString("MenuItemID");
                int number = Integer.parseInt(lastID.substring(1));
                nextID = String.format("M%03d", number + 1);
            } else {
                nextID = "M001";
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return nextID;
    }

    public static String saveImage(File sourceFile, String itemName) throws IOException {
        // Create a unique filename with the item name and timestamp
        String fileExtension = getFileExtension(sourceFile.getName());
        String uniqueFilename = "_" + System.currentTimeMillis() + fileExtension;

        // Define the destination path in the required format
        Path destinationPath = Paths.get(IMAGES_DIR, uniqueFilename);

        // Copy the image file to the destination path
        Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the path in "src\main\resources\assets\food\_1730462732775.jpg" format
        return destinationPath.toString();
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
