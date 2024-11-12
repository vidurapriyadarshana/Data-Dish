package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.TableDto;
import edu.ijse.datadish.model.TableViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    private final TableViewModel tableViewModel = new TableViewModel();

    @FXML
    void addTableAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colCapacity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject());

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<TableDto> tables = tableViewModel.getAllTables();
        loadTable.setItems(tables);
    }
}
