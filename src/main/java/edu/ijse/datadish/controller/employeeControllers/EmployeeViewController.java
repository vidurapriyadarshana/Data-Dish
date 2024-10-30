package edu.ijse.datadish.controller.employeeControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class EmployeeViewController {

    @FXML
    private AnchorPane addEmployeeAnchor;

    @FXML
    private Button btnAddEmployee;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> comStatus;

    @FXML
    private TableView<?> employeeTable;

    @FXML
    void addEmployeeAction(ActionEvent event) {

    }

}
