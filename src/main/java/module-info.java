module edu.ijse.restaurant {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ijse.datadish.controller to javafx.fxml;
    exports edu.ijse.datadish;
}