package edu.ijse.datadish.controller;

import edu.ijse.datadish.model.AddEmployeeModel;
import edu.ijse.datadish.model.AddFoodItemModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    private Label lblEmpId;

    @FXML
    private AnchorPane mainAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblEmpId.setText(AddEmployeeModel.generateNextID());
    }
}
