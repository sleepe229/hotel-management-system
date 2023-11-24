package com.example.trash;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class UserLoggedInController implements Initializable {

    public TableColumn table_column_c1;
    public TableColumn table_column_c2;
    public TableColumn table_column_c3;
    public TableColumn table_column_c4;
    public TextField tf_room_founder_id;
    public Button button_room_found;
    public Button button_book_room;
    public TextField tf_hotel_id;
    public TextField tf_room_number;
    public Button button_booked_rooms;
    @FXML
    private Button button_logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBAuthUtils.changeScene(actionEvent, "hello-view.fxml", "Log in!", null, null);
            }
        });
        button_room_found.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBHotelUtils.founderRooms(actionEvent, tf_room_founder_id.getText());
                table_column_c1.setText("id");
                table_column_c2.setText("type");
                table_column_c3.setText("status");
                table_column_c4.setText("number");

            }
        });
    }

}
