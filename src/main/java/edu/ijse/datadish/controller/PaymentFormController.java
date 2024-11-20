package edu.ijse.datadish.controller;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.OrderDto;
import edu.ijse.datadish.dto.OrderItemDto;
import edu.ijse.datadish.dto.OrderTableDto;
import edu.ijse.datadish.dto.PaymentDto;
import edu.ijse.datadish.model.PaymentFormModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    @FXML
    private Label lblpaymentID;

    @FXML
    private Button btCompleteOrder;

    @FXML
    private VBox orderdItemLoad;

    @FXML
    private Label setCustomerContact;

    @FXML
    private Label setCustomerName;

    @FXML
    private Label setDate;

    @FXML
    private AnchorPane mainAnchor;

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
    private OrderDto orderDto;
    private String paymentId;

    private PaymentDto paymentDto = new PaymentDto();

    @FXML
    void completeOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        paymentDto.setOrderId(orderDto.getOrderId());
        paymentDto.setPayId(paymentId);
        paymentDto.setDate(setDate.getText());
        paymentDto.setAmount(orderDto.getTotalAmount());

        boolean result = PaymentFormModel.completeOrder(orderDto, paymentDto);

        if (result) {
            showAlert("Order Completed", "Order Completed Successfully");
            printBill();

            Stage stage = (Stage) mainAnchor.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Order Failed", "Order Failed");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblpaymentID.setText(PaymentFormModel.generateNextPayID());
        this.paymentId = lblpaymentID.getText();

        setDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public void setOrderId(OrderTableDto order) throws SQLException, ClassNotFoundException {
        setOrderId.setText(order.getOrderId());

        orderItems = PaymentFormModel.getItemDetails(order.getOrderId());
        orderDto = PaymentFormModel.getCustomerDetails(order.getOrderId());

        setTotalAfterDiscount.setText(orderDto.getTotalAmount());
        setEmployeeName.setText(order.getEmployeeId());
        setCustomerName.setText(orderDto.getCustomerName());
        setCustomerContact.setText(orderDto.getCustomerContact());

        orderdItemLoad.getChildren().clear();

        Label headerFoodName = new Label("Name");
        Label headerQuantity = new Label("Qty");
        Label headerPrice = new Label("Price");
        Label headerTotalPrice = new Label("Total Price");

        headerFoodName.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        headerQuantity.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        headerPrice.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        headerTotalPrice.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        gridPane.add(headerFoodName, 0, 0);
        gridPane.add(headerQuantity, 1, 0);
        gridPane.add(headerPrice, 2, 0);
        gridPane.add(headerTotalPrice, 3, 0);

        int row = 1;
        for (OrderItemDto item : orderItems) {
            Label foodNameLabel = new Label(item.getFoodName());
            Label quantityLabel = new Label(String.valueOf(item.getQuantity()));
            Label priceLabel = new Label("LKR " + String.format("%.2f", item.getPrice()));
            Label totalPriceLabel = new Label("LKR " + String.format("%.2f", item.getQuantity() * item.getPrice()));

            foodNameLabel.setStyle("-fx-font-weight: bold;");
            quantityLabel.setStyle("-fx-font-style: italic;");
            priceLabel.setStyle("-fx-text-fill: black;");
            totalPriceLabel.setStyle("-fx-text-fill: black;");

            gridPane.add(foodNameLabel, 0, row);
            gridPane.add(quantityLabel, 1, row);
            gridPane.add(priceLabel, 2, row);
            gridPane.add(totalPriceLabel, 3, row);

            row++;
        }

        orderdItemLoad.getChildren().add(gridPane);
    }

    public void printBill() {
        String orderId = setOrderId.getText();
        System.out.println("orderId: " + orderId);

        if(orderId == null || orderId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an order first!").show();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/FoodBill.jrxml")
            );

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("OrderID",orderId);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate the report!").show();
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Database error!").show();
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
