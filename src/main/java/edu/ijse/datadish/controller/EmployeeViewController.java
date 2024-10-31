package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.model.EmployeeViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
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
    }
}
