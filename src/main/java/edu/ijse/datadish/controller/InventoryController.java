package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.InventoryDto;
import edu.ijse.datadish.model.InventoryModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private TableColumn<InventoryDto, String> colId;

    @FXML
    private TableColumn<InventoryDto, String> colName;

    @FXML
    private TableColumn<InventoryDto, Integer> colQty;

    @FXML
    private TableColumn<InventoryDto, Integer> colStock;

    @FXML
    private TableView<InventoryDto> inventoryTable;

    private InventoryModel inventoryModel = new InventoryModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colQty.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQty()).asObject());
        colStock.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStockLevel()).asObject());

        loadInventoryData();
    }
    
    private void loadInventoryData() {
        try {
            ObservableList<InventoryDto> inventoryItems = inventoryModel.getAllInventoryItems();
            inventoryTable.setItems(inventoryItems);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
