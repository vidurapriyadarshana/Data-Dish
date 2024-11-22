package edu.ijse.datadish.controller;

import edu.ijse.datadish.db.DBConnection;
import edu.ijse.datadish.dto.*;
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
    private Button btSendMail;

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
    private String notificationId;
    private NotificationDto notificationDto = new NotificationDto();

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

    @FXML
    void sendMailOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerEmail = txtEmail.getText().trim();
        if (customerEmail.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid email address!").show();
            return;
        }

        String orderId = setOrderId.getText();
        String customerName = setCustomerName.getText();
        String orderDate = setDate.getText();
        String totalAmount = setTotalAfterDiscount.getText();

        if (orderId == null || orderId.isEmpty() || customerName == null || customerName.isEmpty() || totalAmount == null || totalAmount.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Order details are incomplete!").show();
            return;
        }

        String subject = "Your Order Details - Order ID: " + orderId;
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Dear ").append(customerName).append(",\n\n")
                .append("Thank you for your order with us! Below are your order details:\n\n")
                .append("Order ID: ").append(orderId).append("\n")
                .append("Order Date: ").append(orderDate).append("\n")
                .append("Total Amount: ").append(totalAmount).append("\n\n")
                .append("Ordered Items:\n");

        for (OrderItemDto item : orderItems) {
            emailBody.append("- ")
                    .append(item.getFoodName())
                    .append(" (Qty: ").append(item.getQuantity())
                    .append(", Price: LKR ").append(String.format("%.2f", item.getPrice())).append(")\n");
        }

        emailBody.append("\nWe hope you enjoy your meal!\n\n")
                .append("Best Regards,\nData Dish Restaurant");

        CustMailSenderController mailSender = new CustMailSenderController();
        mailSender.sendEmail(customerEmail, subject, emailBody.toString());

        this.notificationId = PaymentFormModel.generateNotificationId();

        notificationDto.setNotificationId(notificationId);
        notificationDto.setCustomerId(orderDto.getCustomerId());
        notificationDto.setDesc(orderId);
        notificationDto.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        System.out.println(notificationId);
        System.out.println(orderDto.getCustomerId());
        System.out.println("Order ID: " + orderId);
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        boolean result = PaymentFormModel.saveNotification(notificationDto);

        if(result) {
            showAlert("Notification Sent", "Notification Sent Successfully");
        }else {
            showAlert("Error", "Failed to send notification");
        }
    }


}