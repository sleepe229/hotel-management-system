module com.example.agpsdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.agpsdesktopapp to javafx.fxml;
    exports com.example.agpsdesktopapp;
}