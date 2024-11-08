package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.InventoryDto;
import edu.ijse.datadish.model.AddInventoryItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddInventoryItemController implements Initializable {

    @FXML
    private Button btAddItem;

    @FXML
    private Label lblSerID;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemQty;

    @FXML
    private TextField txtStockLevel;

    private InventoryDto inventoryDto = new InventoryDto();

    @FXML
    void addItemAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblSerID.getText();
        String name = txtItemName.getText();
        int qty = Integer.parseInt(txtItemQty.getText());
        int stockLevel = Integer.parseInt(txtStockLevel.getText());

        inventoryDto.setId(id);
        inventoryDto.setName(name);
        inventoryDto.setQty(qty);
        inventoryDto.setStockLevel(stockLevel);

        boolean result = AddInventoryItemModel.addItem(inventoryDto);

        if(result){
            showAlert("Add Item", "Item Added Successfully");
            closeWindow();
        }else{
            showAlert("Add Item", "Item Added Unsuccessfully");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btAddItem.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSerID.setText(AddInventoryItemModel.generateNextID());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
