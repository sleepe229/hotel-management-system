package com.example.trash.Controllers;

import com.example.trash.DBUtils.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.trash.DBUtils.OtherUtils.isEmptyField;

public class UserLoggedInController implements Initializable {

    public TableColumn<String, String> table_column_c1;
    public TableColumn<String, String> table_column_c2;
    public TableColumn<String, String> table_column_c3;
    public TableColumn<String, String> table_column_c4;
    public TextField tf_room_founder_id;
    public Button button_room_find;
    public Button button_book_room;
    public TextField tf_hotel_id;
    public TextField tf_room_number;
    public Button button_booked_rooms;

    public TableView<Object> table_view;
    public Button button_hotel_reload;
    public TextField tf_client_username;
    public TextField tf_client_email;
    public TextField tf_client_phone_number;

    @FXML
    private Button button_logout;

    public static String LAST_USER_LOGIN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadHotelList(new ActionEvent());
        
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OtherUtils.changeScene(actionEvent, "/com/example/trash/hello-view.fxml", "Log in!", null, null);
            }
        });
        button_room_find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!isEmptyField(tf_room_founder_id.getText())) {
                    table_column_c1.setText("hotel_id");
                    table_column_c2.setText("type of room");
                    table_column_c3.setText("status");
                    table_column_c4.setText("number of room");
                    table_view.setItems(DBRoomFinder.finderRooms(actionEvent, tf_room_founder_id.getText()));
                    table_column_c1.setCellValueFactory(new PropertyValueFactory<>("hotel_id"));
                    table_column_c2.setCellValueFactory(new PropertyValueFactory<>("type"));
                    table_column_c3.setCellValueFactory(new PropertyValueFactory<>("status"));
                    table_column_c4.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
                }
            }
        });
        button_hotel_reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                reloadHotelList(actionEvent);
            }
        });
        button_book_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBBookingRoom.bookRoom(actionEvent, tf_hotel_id.getText(), tf_room_number.getText(), LAST_USER_LOGIN, tf_client_phone_number.getText(), tf_client_username.getText(), tf_client_email.getText());
            }
        });
        button_booked_rooms.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OtherUtils.changeScene(actionEvent, "/com/example/trash/user-booked.fxml", "booked room", null, null);
            }
        });
    }
    void reloadHotelList(ActionEvent actionEvent){
        table_column_c1.setText("hotel_id");
        table_column_c2.setText("name");
        table_column_c3.setText("stars");
        table_column_c4.setText("location");
        table_view.setItems(DBHotelFinder.finderHotels(actionEvent));
        table_column_c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_column_c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_column_c3.setCellValueFactory(new PropertyValueFactory<>("stars"));
        table_column_c4.setCellValueFactory(new PropertyValueFactory<>("location"));
    }
}
