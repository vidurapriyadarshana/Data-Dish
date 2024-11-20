package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.TableDto;
import edu.ijse.datadish.model.TableViewModel;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private Button btAddTable;

    @FXML
    private TableColumn<TableDto, Integer> colCapacity;

    @FXML
    private TableColumn<TableDto, String> colId;

    @FXML
    private TableColumn<TableDto, String> colStatus;

    @FXML
    private TableView<TableDto> loadTable;

    @FXML
    private TableColumn<TableDto, Void> colActions;

    private final TableViewModel tableViewModel = new TableViewModel();

    @FXML
    void addTableAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTableView.fxml"));
        Parent load = loader.load();

        Stage addItemStage = new Stage();
        addItemStage.setTitle("Add Table");
        addItemStage.setScene(new Scene(load));
        addItemStage.initModality(Modality.NONE);
        addItemStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colCapacity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject());

        addActionsColumn();

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<TableDto> tables = tableViewModel.getAllTables();
        loadTable.setItems(tables);
    }

    private void addActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final CheckBox statusCheckBox = new CheckBox();

            {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                deleteButton.setOnAction(event -> {
                    TableDto tableDto = getTableView().getItems().get(getIndex());
                    handleDeleteAction(tableDto);
                });

                statusCheckBox.setOnAction(event -> {
                    TableDto tableDto = getTableView().getItems().get(getIndex());
                    handleStatusToggle(tableDto, statusCheckBox.isSelected());
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionBox = new HBox(10, statusCheckBox, deleteButton);
                    actionBox.setStyle("-fx-alignment: center;");

                    setGraphic(actionBox);

                    TableDto tableDto = getTableView().getItems().get(getIndex());
                    statusCheckBox.setSelected("Reserved".equals(tableDto.getStatus()));
                }
            }
        });
    }


    private void handleDeleteAction(TableDto tableDto) {
        boolean isDeleted = tableViewModel.deleteTable(tableDto.getId());
        if (isDeleted) {
            loadTable.getItems().remove(tableDto);
            showAlert("Table Deleted", "Table ID " + tableDto.getId() + " has been deleted successfully.");
        } else {
            showAlert("Delete Failed", "Failed to delete table ID " + tableDto.getId());
        }
    }

    private void handleStatusToggle(TableDto tableDto, boolean isReserved) {
        String newStatus = isReserved ? "Reserved" : "Available";
        boolean isUpdated = tableViewModel.updateTableStatus(tableDto.getId(), newStatus);

        if (isUpdated) {
            tableDto.setStatus(newStatus);
            loadTable.refresh();
            showAlert("Status Updated", "Table ID " + tableDto.getId() + " is now " + newStatus + ".");
        } else {
            showAlert("Update Failed", "Failed to update status for table ID " + tableDto.getId());
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
