package edu.ijse.datadish.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class HomePageController {

    @FXML
    private GridPane foodContainer;

    @FXML
    private AnchorPane itemAnchor;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private AnchorPane menuAnchor;

    @FXML
    private ScrollPane scrollPanle;

    @FXML
    private TextField searchField;

    List<FoodController> foodControllers = new ArrayList<>();

}
