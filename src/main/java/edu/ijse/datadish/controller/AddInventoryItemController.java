package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.AddInventoryItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
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

    @FXML
    void addItemAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSerID.setText(AddInventoryItemModel.generateNextID());
    }
}
