module edu.ijse.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires mysql.connector.j;
    requires java.desktop;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires jbcrypt;


    opens edu.ijse.datadish.controller to javafx.fxml;
    exports edu.ijse.datadish;
}