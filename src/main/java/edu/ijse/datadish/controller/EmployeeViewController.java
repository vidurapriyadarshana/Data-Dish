package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.EmployeeDto;
import edu.ijse.datadish.dto.SalaryDto;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

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
    private AnchorPane mainAnchor;

    @FXML
    private TableView<SalaryDto> tableSalary;

    @FXML
    private TableColumn<SalaryDto, String> colsalary;

    @FXML
    private TableColumn<SalaryDto, String> colSalaryDate;

    @FXML
    private TableColumn<SalaryDto, String> colSalaryId;

    @FXML
    private TableColumn<SalaryDto, String> colEmployeeName;

    @FXML
    private TableColumn<SalaryDto, String> colSalaryAction;

    @FXML
    void addEmployeeAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddEmployee.fxml"));
            Parent load = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Employee");
            stage.setScene(new Scene(load));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            reloadEmployeeTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeViewModel = new EmployeeViewModel();
        loadSalaryTable();
        reloadEmployeeTable();

        colActions.setCellFactory(new Callback<>() {
            @Override
            public TableCell<EmployeeDto, String> call(TableColumn<EmployeeDto, String> param) {
                return new TableCell<>() {
                    private final Button showInfo = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        showInfo.setId("btnEdit");
                        deleteButton.setId("btnDelete");

                        showInfo.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                        showInfo.setOnAction(event -> {
                            EmployeeDto employeeDto = getTableView().getItems().get(getIndex());
                            updateEmployee(employeeDto);
                        });

                        deleteButton.setOnAction(event -> {
                            EmployeeDto employeeDto = getTableView().getItems().get(getIndex());
                            try {
                                deleteEmployee(employeeDto);
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

    private void reloadEmployeeTable() {
        try {
            ObservableList<EmployeeDto> employees = employeeViewModel.loadEmpTable();
            employeeTable.setItems(employees);
            colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeID()));
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeName()));
            colContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeContact()));
            colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
            colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeStatus()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateEmployee(EmployeeDto employeeDto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateEmployee.fxml"));
            Parent root = loader.load();
            UpdateEmployeeController controller = loader.getController();
            controller.setEmployeeData(employeeDto);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            reloadEmployeeTable();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        boolean isDeleted = employeeViewModel.deleteEmployee(employeeDto);

        if (isDeleted) {
            showAlert("Success", "Employee Deleted Successfully");
            reloadEmployeeTable();
        } else {
            showAlert("Error", "Employee Deletion Failed");
        }
    }

    private void loadSalaryTable() {
        try {
            ObservableList<SalaryDto> salaries = employeeViewModel.loadSalaryTable();
            tableSalary.setItems(salaries);

            colSalaryId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSalaryId()));
            colEmployeeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeId()));
            colSalaryDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
            colsalary.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSalary(SalaryDto salaryDto) {

    }

    @FXML
    void addSalaryAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddEmployeeSalary.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        reloadEmployeeTable();
    }

    private void showAlert(String title, String message) {
        Alert.AlertType alertType = "Error".equals(title) ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
