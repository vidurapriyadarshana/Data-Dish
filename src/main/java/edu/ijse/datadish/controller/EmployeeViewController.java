package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.FoodDto;
import edu.ijse.datadish.model.EmployeeViewModel;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable  {

    @FXML
    private Button btAddEmployee;

    @FXML
    private TableColumn<EmployeeDto, String> colActions;

    @FXML
    private TableColumn<EmployeeDto, String> colAddress;

    @FXML
    private TableColumn<EmployeeDto, String> colContact;

    @FXML
    private TableColumn<EmployeeDto, String> colId;

    @FXML
    private TableColumn<EmployeeDto, String> colName;

    @FXML
    private TableColumn<EmployeeDto, String> colStatus;

    @FXML
    private TableView<EmployeeDto> employeeTable;

    private EmployeeViewModel employeeViewModel;
    @FXML
    void addEmployeeAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeViewModel = new EmployeeViewModel();
        try {
            ObservableList<EmployeeDto> employees = employeeViewModel.loadEmpTable();
            employeeTable.setItems(employees);
            colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeID()));
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeName()));
            colContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeContact()));
            colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
            colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeStatus()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        colActions.setCellFactory(new Callback<>() {
            @Override
            public TableCell<EmployeeDto, String> call(TableColumn<EmployeeDto, String> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setId("btnEdit");
                        deleteButton.setId("btnDelete");

                        editButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00FF9C; -fx-text-fill: black;");
                        deleteButton.setStyle("-fx-background-color: transparent; -fx-border-color: #F95454; -fx-text-fill: black;");

                        editButton.setOnAction(event -> {
                            EmployeeDto employeeDto = getTableView().getItems().get(getIndex());
                            try {
                                editFoodItem(employeeDto);
                            } catch (IOException | SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setOnAction(event -> {
                            EmployeeDto employeeDto = getTableView().getItems().get(getIndex());
                            deleteFoodItem(employeeDto);
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

    private void editFoodItem(EmployeeDto employeeDto) throws IOException, SQLException, ClassNotFoundException {

    }

    private void deleteFoodItem(EmployeeDto employeeDto) {

    }
}
