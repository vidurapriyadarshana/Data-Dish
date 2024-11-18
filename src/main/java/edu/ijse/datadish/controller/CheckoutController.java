package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.OrderTableDto;
import edu.ijse.datadish.model.CheckoutModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CheckoutController {

    @FXML
    private Button btnSearch;

    @FXML
    private GridPane ordersGrid;

    @FXML
    private TextField searchBar;

    private final CheckoutModel checkoutModel = new CheckoutModel();

    @FXML
    public void initialize() {
        try {
            // Load incomplete orders from the database
            List<OrderTableDto> orders = checkoutModel.loadIncompleteOrders();

            // Dynamically populate the GridPane with order details
            int row = 0;
            int column = 0;
            for (OrderTableDto order : orders) {
                VBox orderDetails = createOrderDetailsVBox(order);

                // Add the VBox with order details to the GridPane at the correct row and column
                ordersGrid.add(orderDetails, column, row);

                // Increment column to move to the next column
                column++;
                if (column == 5) {
                    column = 0; // Reset to first column and move to the next row
                    row++;
                }
            }
        } catch (Exception e) {
            showError("Failed to load orders: " + e.getMessage());
        }
    }

    private VBox createOrderDetailsVBox(OrderTableDto order) {
        // Create VBox to hold the order details
        VBox vbox = new VBox(5);  // 5px spacing between labels

        // Add labels for each order detail
        vbox.getChildren().add(new Label("Order ID: " + order.getOrderId()));
        vbox.getChildren().add(new Label("Employee ID: " + order.getEmployeeId()));
        vbox.getChildren().add(new Label("Total Amount: " + order.getTotalAmount()));
        vbox.getChildren().add(new Label("Table ID: " + order.getTableId()));
        vbox.getChildren().add(new Label("Status: " + order.getStatus()));

        // Add a complete button for each order (you can modify this based on your business logic)
        Button completeOrderButton = new Button("Complete Order");
        completeOrderButton.setOnAction(event -> handleCompleteOrder(order));
        vbox.getChildren().add(completeOrderButton);

        return vbox;
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String query = searchBar.getText().trim().toLowerCase();

        // Filter orders by Order ID or Table ID
        ordersGrid.getChildren().clear();  // Clear the existing grid

        try {
            List<OrderTableDto> orders = checkoutModel.loadIncompleteOrders();

            // Dynamically repopulate the GridPane based on the search query
            int row = 0;
            int column = 0;
            for (OrderTableDto order : orders) {
                if (order.getOrderId().toLowerCase().contains(query) || order.getTableId().toLowerCase().contains(query)) {
                    VBox orderDetails = createOrderDetailsVBox(order);
                    ordersGrid.add(orderDetails, column, row);
                    column++;
                    if (column == 5) {
                        column = 0;
                        row++;
                    }
                }
            }
        } catch (Exception e) {
            showError("Failed to load orders: " + e.getMessage());
        }
    }

    private void handleCompleteOrder(OrderTableDto order) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Complete Order");
        alert.setHeaderText("Are you sure you want to complete this order?");
        alert.setContentText("Order ID: " + order.getOrderId());

        if (alert.showAndWait().isPresent()) {
            // Proceed with completing the order
            try {
                checkoutModel.completeOrder(order.getOrderId());
                showInfo("Order completed successfully!");
                // Optionally, update the order's status in the UI after completion
            } catch (Exception e) {
                showError("Failed to complete the order: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
