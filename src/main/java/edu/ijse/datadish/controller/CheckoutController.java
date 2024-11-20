package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.OrderTableDto;
import edu.ijse.datadish.model.CheckoutModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            List<OrderTableDto> orders = checkoutModel.loadIncompleteOrders();

            int row = 0;
            int column = 0;
            for (OrderTableDto order : orders) {
                VBox orderDetails = createOrderDetailsVBox(order);

                ordersGrid.add(orderDetails, column, row);

                column++;
                if (column == 5) {
                    column = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            showError("Failed to load orders: " + e.getMessage());
        }
    }

    private VBox createOrderDetailsVBox(OrderTableDto order) {

        VBox vbox = new VBox(5);
        vbox.setStyle("-fx-padding: 5; -fx-border-color: #FF971D; -fx-border-width: 1");
        vbox.getChildren().add(new Label("Order ID: " + order.getOrderId()));
        vbox.getChildren().add(new Label("Employee ID: " + order.getEmployeeId()));
        vbox.getChildren().add(new Label("Total Amount: " + order.getTotalAmount()));
        vbox.getChildren().add(new Label("Table ID: " + order.getTableId()));
        vbox.getChildren().add(new Label("Status: " + order.getStatus()));

        Button completeOrderButton = new Button("Complete Order");
        completeOrderButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00FF9C; -fx-text-fill: black;");
        completeOrderButton.setOnAction(event -> handleCompleteOrder(order));
        vbox.getChildren().add(completeOrderButton);

        return vbox;
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String query = searchBar.getText().trim().toLowerCase();

        ordersGrid.getChildren().clear();

        try {
            List<OrderTableDto> orders = checkoutModel.loadIncompleteOrders();

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentForm.fxml"));
            Parent root = loader.load();

            PaymentFormController controller = loader.getController();
            controller.setOrderId(order);
            Stage stage = new Stage();
            stage.setTitle("Complete Payment");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            initialize();
        } catch (Exception e) {
            showError("Failed to open payment form: " + e.getMessage());
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
