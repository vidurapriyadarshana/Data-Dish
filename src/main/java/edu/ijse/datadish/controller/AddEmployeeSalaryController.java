package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.AddEmployeeSalaryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    void addSalaryAction(ActionEvent event) {

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
}
