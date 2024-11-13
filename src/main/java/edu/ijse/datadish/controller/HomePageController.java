package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.HomePageModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private AnchorPane itemAnchor, mainAnchor, menuAnchor;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane foodContainer;

    @FXML
    private VBox cartContainer;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblEmpId;


    private Map<FoodDto, Integer> cartItems = new HashMap<>();
    private double totalPrice = 0.0;

//    public void initialize() {
//
//    }

    private void loadMenuItems() {
        List<FoodDto> foodItems = HomePageModel.getAllMenuItems();
        int row = 0;
        int col = 0;

        try {
            for (FoodDto foodItem : foodItems) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Food.fxml"));
                AnchorPane foodPane = loader.load();

                FoodController controller = loader.getController();
                controller.setData(foodItem);
                controller.setAddToCartAction(() -> addToCart(foodItem));

                foodContainer.add(foodPane, col, row);

                col++;
                if (col >= 3) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToCart(FoodDto foodItem) {
        cartItems.put(foodItem, cartItems.getOrDefault(foodItem, 0) + 1);
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        cartContainer.getChildren().clear();
        totalPrice = 0.0;

        for (Map.Entry<FoodDto, Integer> entry : cartItems.entrySet()) {
            FoodDto food = entry.getKey();
            int qty = entry.getValue();
            double itemTotal = food.getFoodPrice() * qty;
            totalPrice += itemTotal;

            HBox cartItem = new HBox(10);
            cartItem.setStyle("-fx-padding: 5; -fx-border-color: #FF971D; -fx-border-width: 1;");

            Label nameLabel = new Label(food.getFoodName());
            Label qtyLabel = new Label("Qty: " + qty);
            Label priceLabel = new Label("LKR" + String.format("%.2f", itemTotal));

            cartItem.getChildren().addAll(nameLabel, qtyLabel, priceLabel);
            cartContainer.getChildren().add(cartItem);
        }

        totalPriceLabel.setText("Total: LKR" + String.format("%.2f", totalPrice));
    }

    @FXML
    private void checkoutAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Checkout");
        alert.setHeaderText(null);
        alert.setContentText("Checkout amount is: LKR" + String.format("%.2f", totalPrice));
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenuItems();
        lblOrderId.setText(HomePageModel.generateNextID());
        //lblEmpId.setText();
    }
}
