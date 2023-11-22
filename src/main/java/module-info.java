module com.example.agpsdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.trash to javafx.fxml;
    exports com.example.trash;
}