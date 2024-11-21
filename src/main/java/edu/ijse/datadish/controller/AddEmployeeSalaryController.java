package edu.ijse.datadish.controller;

import edu.ijse.datadish.dto.SalaryDto;
import edu.ijse.datadish.model.AddEmployeeSalaryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddEmployeeSalaryController implements Initializable {

    @FXML
    private Button btnAddSalary;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblSalaryID;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private ChoiceBox<String> chooseEmployee;

    private SalaryDto salaryDto = new SalaryDto();

    @FXML
    void addSalaryAction(ActionEvent event) {
        try {
            String salaryID = lblSalaryID.getText();
            String employeeName = chooseEmployee.getValue();
            String paymentDate = lblDate.getText();
            String salaryText = txtSalary.getText();

            if (employeeName.isEmpty() || salaryText.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            double amount = Double.parseDouble(salaryText);
            String employeeId = AddEmployeeSalaryModel.getEmployeeId(employeeName);

            if (employeeId == null) {
                showAlert("Error", "Employee not found. Please select a valid employee.");
                return;
            }

            SalaryDto salaryDto = new SalaryDto();
            salaryDto.setSalaryId(salaryID);
            salaryDto.setEmployeeId(employeeId);
            salaryDto.setDate(paymentDate);
            salaryDto.setAmount(amount);

            boolean isAdded = AddEmployeeSalaryModel.addSalary(salaryDto);

            if (isAdded) {
                showAlert("Add Salary", "Salary added successfully!");
                Stage stage = (Stage) mainAnchor.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Add Salary", "Failed to add salary.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid salary amount. Please enter a valid number.");
        } catch (SQLException | ClassNotFoundException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSalaryID.setText(AddEmployeeSalaryModel.generateNextID());
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        try {
            chooseEmployee.getItems().addAll(AddEmployeeSalaryModel.getEmployeeNames()); // No changes required here
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
