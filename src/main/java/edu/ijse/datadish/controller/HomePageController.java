package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.HomePageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
            nameLabel.setPrefWidth(60);
            Label qtyLabel = new Label("Qty: " + qty);
            qtyLabel.setPrefWidth(40);
            Label priceLabel = new Label("LKR" + String.format("%.2f", itemTotal));
            priceLabel.setPrefWidth(70);

            Button addButton = new Button("+");
            addButton.setOnAction(e -> addToCart(food));
            addButton.setPrefWidth(30);

            Button removeButton = new Button("-");
            removeButton.setOnAction(e -> removeFromCart(food));
            removeButton.setPrefWidth(30);

            HBox buttonContainer = new HBox(5, addButton, removeButton);
            buttonContainer.setAlignment(Pos.CENTER_RIGHT);

            addButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00FF9C; -fx-text-fill: black;");
            removeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #F95454; -fx-text-fill: black;");

            cartItem.getChildren().addAll(nameLabel, qtyLabel, priceLabel, buttonContainer);
            cartContainer.getChildren().add(cartItem);
        }

        totalPriceLabel.setText("Total: LKR" + String.format("%.2f", totalPrice));
    }


    private void removeFromCart(FoodDto foodItem) {
        int currentQty = cartItems.getOrDefault(foodItem, 0);
        if (currentQty > 1) {
            cartItems.put(foodItem, currentQty - 1);
        } else {
            cartItems.remove(foodItem);
        }
        updateCartDisplay();
    }

    @FXML
    private void checkoutAction() {
        if (cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cart is empty");
            alert.showAndWait();
        }
    }

    @FXML
    void checkoutAction(ActionEvent event) {
        Alert alert;
        if (cartItems.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cart is empty");
        }else{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm checkout?");

        }
        alert.showAndWait();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenuItems();
        lblOrderId.setText(HomePageModel.generateNextID());
    }
}