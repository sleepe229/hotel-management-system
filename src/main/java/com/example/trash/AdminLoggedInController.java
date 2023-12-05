package com.example.trash;

import com.example.trash.DBUtils.DBHotelUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoggedInController implements Initializable {

    public TextField tf_hotel_id;
    public TextField tf_hotel_name;
    public TextField tf_hotel_stars;
    public TextField tf_room_number;
    public TextField tf_room_type;
    public TextField tf_hotel_for_room_id;
    public TextField tf_hotel_location;
    public TextField tf_room_status;
    public Button button_add_hotel;
    public Button button_remove_hotel;
    public Button button_add_room;
    public Button button_remove_room;

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
        button_add_hotel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBHotelUtils.actionHotel(actionEvent, tf_hotel_id.getText(), tf_hotel_name.getText(), tf_hotel_stars.getText(), tf_hotel_location.getText(), "add");
            }
        });
        button_remove_hotel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBHotelUtils.actionHotel(actionEvent, tf_hotel_id.getText(), tf_hotel_name.getText(), tf_hotel_stars.getText(), tf_hotel_location.getText(), "delete");
            }
        });
        button_add_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBHotelUtils.actionRoom(actionEvent, tf_hotel_for_room_id.getText(), tf_room_type.getText(), tf_room_status.getText(), tf_room_number.getText(), "add");
            }
        });
        button_remove_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBHotelUtils.actionRoom(actionEvent, tf_hotel_for_room_id.getText(), tf_room_type.getText(), tf_room_status.getText(), tf_room_number.getText(), "delete");
            }
        });
    }

}
