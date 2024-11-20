package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.InventoryDto;
import edu.ijse.datadish.model.InventoryModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private Button btAddItem;

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

    @FXML
    private TableColumn<InventoryDto, String> colAction;


    private InventoryModel inventoryModel = new InventoryModel();

    @FXML
    void addItemAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/AddInventoryItemView.fxml"));
        Stage addItemStage = new Stage();
        addItemStage.setTitle("Add Item");
        addItemStage.setScene(new Scene(load));
        addItemStage.initModality(Modality.NONE);
        addItemStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colQty.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQty()).asObject());
        colStock.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStockLevel()).asObject());

        loadInventoryData();

        colAction.setCellFactory(new Callback<>() {
            @Override
            public TableCell<InventoryDto, String> call(TableColumn<InventoryDto, String> inventoryDtoStringTableColumn) {
                return new TableCell<>() {
                    private final Button showInfo = new Button("Info");
                    private final Button deleteButton = new Button("Remove");

                    {
                        showInfo.setId("btnEdit");
                        deleteButton.setId("btnDelete");

                        showInfo.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");


                        showInfo.setOnAction(event -> {
                            InventoryDto inventoryDto = getTableView().getItems().get(getIndex());
                            try {
                                getInfo(inventoryDto);
                            } catch (IOException | SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setOnAction(event -> {
                            InventoryDto inventoryDto = getTableView().getItems().get(getIndex());
                            try {
                                removeItem(inventoryDto);
                            } catch (SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });

                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, showInfo, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }

        });

    }

    private void getInfo(InventoryDto inventoryDto) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("button clicked");
    }

    private void removeItem(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        boolean result = inventoryModel.removeItem(inventoryDto.getId());

        if(result){
            loadInventoryData();
            showAlert("Remove Item", "Item Removed Successfully");
        }else{
            showAlert("Remove Item", "Item Removal Failed");
        }
    }


    private void loadInventoryData() {
        try {
            ObservableList<InventoryDto> inventoryItems = inventoryModel.getAllInventoryItems();
            inventoryTable.setItems(inventoryItems);
        } catch (SQLException | ClassNotFoundException e) {
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
