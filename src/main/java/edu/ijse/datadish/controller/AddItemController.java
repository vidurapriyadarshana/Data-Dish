package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddItemController {

    @FXML
    private AnchorPane addItemAnchor;

    @FXML
    private TableColumn<FoodDto, String> colCategory;

    @FXML
    private TableColumn<FoodDto, String> colDesc;

    @FXML
    private TableColumn<FoodDto, String> colId;

    @FXML
    private TableColumn<FoodDto, String> colName;

    @FXML
    private TableColumn<FoodDto, String> colPrice;

    @FXML
    private TableColumn<FoodDto, String> colStatus;

    @FXML
    private TableView<FoodDto> itemMenuTable;

    @FXML
    void addItemAction(ActionEvent event) throws IOException {
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/AddFoodItem.fxml"));
            Stage addItemStage = new Stage();
            addItemStage.setTitle("Add Item");
            addItemStage.setScene(new Scene(load));
            addItemStage.initModality(Modality.NONE);
            addItemStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteItemAction(ActionEvent event) {

    }

    @FXML
    void updateItemAction(ActionEvent event) {

    }

}
