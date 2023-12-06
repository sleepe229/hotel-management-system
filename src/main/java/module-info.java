module com.example.agpsdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.example.trash.DBUtils;
    opens com.example.trash.DBUtils to javafx.fxml;
    exports com.example.trash.Models;
    opens com.example.trash.Models to javafx.fxml;
    exports com.example.trash.Controllers;
    opens com.example.trash.Controllers to javafx.fxml;
    opens com.example.trash to javafx.fxml;
}