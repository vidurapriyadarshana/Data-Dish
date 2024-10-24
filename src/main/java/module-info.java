module edu.ijse.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens edu.ijse.datadish.controller to javafx.fxml;
    exports edu.ijse.datadish;
}