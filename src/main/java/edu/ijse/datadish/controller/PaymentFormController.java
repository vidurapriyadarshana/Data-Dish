package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.OrderItemDto;
import edu.ijse.datadish.dto.OrderTableDto;
import edu.ijse.datadish.model.PaymentFormModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    @FXML
    private Button btCompleteOrder;

    @FXML
    private VBox orderdItemLoad;  // VBox where items will be loaded

    @FXML
    private Label setCustomerContact;

    @FXML
    private Label setCustomerName;

    @FXML
    private Label setDate;

    @FXML
    private Label setEmployeeName;

    @FXML
    private Label setOrderId;

    @FXML
    private Label setTime;

    @FXML
    private Label setTotalAfterDiscount;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtEmail;

    private List<OrderItemDto> orderItems;

    @FXML
    void completeOrderOnAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public void setOrderId(OrderTableDto order) throws SQLException, ClassNotFoundException {

        setOrderId.setText(order.getOrderId());

        orderItems = PaymentFormModel.getItemDetails(order.getOrderId());

        orderdItemLoad.getChildren().clear();

        for (OrderItemDto item : orderItems) {
            HBox itemHBox = new HBox(10);
            itemHBox.setAlignment(Pos.CENTER_LEFT);

            Label foodNameLabel = new Label(item.getFoodName());
            Label quantityLabel = new Label("Qty: " + item.getQuantity());
            Label priceLabel = new Label("Price: " + item.getPrice());


            foodNameLabel.setStyle("-fx-font-weight: bold;");
            quantityLabel.setStyle("-fx-font-style: italic;");
            priceLabel.setStyle("-fx-text-fill: white;");

            itemHBox.getChildren().addAll(foodNameLabel, quantityLabel, priceLabel);

            orderdItemLoad.getChildren().add(itemHBox);
        }
    }

}
