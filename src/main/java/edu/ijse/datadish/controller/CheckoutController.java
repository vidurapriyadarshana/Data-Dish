package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.OrderDto;
import edu.ijse.datadish.dto.OrderTableDto;
import edu.ijse.datadish.model.CheckoutModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.List;

public class CheckoutController {

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<OrderTableDto> cheackoutTable;

    @FXML
    private TableColumn<OrderTableDto, String> colAction;

    @FXML
    private TableColumn<OrderTableDto, String> colEmployeeId;

    @FXML
    private TableColumn<OrderTableDto, String> colOrderId;

    @FXML
    private TableColumn<OrderTableDto, String> colTableId;

    @FXML
    private TableColumn<OrderTableDto, String> colTotalAmount;

    @FXML
    private TextField searchBar;

    private final CheckoutModel checkoutModel = new CheckoutModel();

    @FXML
    public void initialize() {
        colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button completeButton = new Button("Complete");

            {
                completeButton.setOnAction(event -> {
                    OrderTableDto order = getTableView().getItems().get(getIndex());
                    handleCompleteOrder(order);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(completeButton);
                }
            }
        });

        try {
            loadTableOrders();
        } catch (SQLException | ClassNotFoundException e) {
            showError("Failed to load orders: " + e.getMessage());
        }
    }

    private void loadTableOrders() throws SQLException, ClassNotFoundException {
        ObservableList<OrderTableDto> orderList = FXCollections.observableArrayList();
        List<OrderTableDto> orders = checkoutModel.loadOrders();
        orderList.addAll(orders);
        cheackoutTable.setItems(orderList);
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String query = searchBar.getText().trim().toLowerCase();
        ObservableList<OrderTableDto> filteredList = cheackoutTable.getItems().filtered(order ->
                order.getOrderId().toLowerCase().contains(query) ||
                        order.getTableId().toLowerCase().contains(query));
        cheackoutTable.setItems(filteredList);
    }

    private void handleCompleteOrder(OrderTableDto order) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Complete Order");
        alert.setHeaderText("Are you sure you want to complete this order?");
        alert.setContentText("Order ID: " + order.getOrderId());

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                checkoutModel.completeOrder(order.getOrderId());
                cheackoutTable.getItems().remove(order);
                showInfo("Order completed successfully!");
            } catch (SQLException | ClassNotFoundException e) {
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
