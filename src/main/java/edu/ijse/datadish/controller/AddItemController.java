package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.AddItemModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

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
        Parent load = FXMLLoader.load(getClass().getResource("/view/AddFoodItem.fxml"));
        Stage addItemStage = new Stage();
        addItemStage.setTitle("Add Item");
        addItemStage.setScene(new Scene(load));
        addItemStage.initModality(Modality.NONE);
        addItemStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<FoodDto> foodItems = new AddItemModel().loadTable();
        itemMenuTable.setItems(foodItems);

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodName()));
        colPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFoodPrice())));
        colCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodCategory()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodAvailability()));

        colDesc.setCellFactory(new Callback<>() {
            @Override
            public TableCell<FoodDto, String> call(TableColumn<FoodDto, String> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setId("btnEdit");
                        deleteButton.setId("btnDelete");

                        editButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00FF9C; -fx-text-fill: black;");
                        deleteButton.setStyle("-fx-background-color: transparent; -fx-border-color: #F95454; -fx-text-fill: black;");

                        editButton.setOnAction(event -> {
                            FoodDto food = getTableView().getItems().get(getIndex());
                            editFoodItem(food);
                        });

                        deleteButton.setOnAction(event -> {
                            FoodDto food = getTableView().getItems().get(getIndex());
                            deleteFoodItem(food);
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, editButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void editFoodItem(FoodDto food) {

        System.out.println("Editing item: " + food.getFoodName());
    }

    private void deleteFoodItem(FoodDto food) {

        System.out.println("Deleting item: " + food.getFoodName());
    }
}
