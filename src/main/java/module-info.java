module edu.ijse.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires mysql.connector.j;


    opens edu.ijse.datadish.controller to javafx.fxml;
    exports edu.ijse.datadish;
    opens edu.ijse.datadish.controller.addItemControllers to javafx.fxml;
    opens edu.ijse.datadish.controller.logInControllers to javafx.fxml;
}