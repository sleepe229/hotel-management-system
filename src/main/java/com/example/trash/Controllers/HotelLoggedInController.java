package com.example.trash.Controllers;

import com.example.trash.DBUtils.DBBookedRoomAction;
import com.example.trash.DBUtils.DBBookedRoomFinder;
import com.example.trash.DBUtils.OtherUtils;
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

public class HotelLoggedInController implements Initializable {

    public TableView<Object> table_view;
    public TableColumn<String, String> table_column_hotel_id;
    public TableColumn<String, String> table_column_room_number;
    public TableColumn<String, String> table_column_client_fullname;
    public TableColumn<String, String> table_column_client_login;
    public TableColumn<String, String> table_column_client_phone_number;
    public TableColumn<String, String> table_column_client_email;
    public Button button_reload_table;
    public Button button_accept_room;
    public Button button_reject_room;
    public TextField tf_hotel_id;
    public TextField tf_room_number;
    public TextField tf_client_login;
    public TableColumn<Object, Object> table_column_room_status;
    @FXML
    private Button button_logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable(new ActionEvent());

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OtherUtils.changeScene(actionEvent, "/com/example/trash/hello-view.fxml", "Log in!", null, null);
            }
        });

        button_accept_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBBookedRoomAction.actionBookingRoom(actionEvent, tf_hotel_id.getText(), tf_room_number.getText(), tf_client_login.getText(), "accept");
                reloadTable(actionEvent);
            }
        });
        button_reject_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBBookedRoomAction.actionBookingRoom(actionEvent, tf_hotel_id.getText(), tf_room_number.getText(), tf_client_login.getText(), "reject");
                reloadTable(actionEvent);
            }
        });
    }

    void reloadTable(ActionEvent actionEvent){
        table_view.setItems(DBBookedRoomFinder.finderBookedRooms(actionEvent, null));
        table_column_hotel_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_column_room_number.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        table_column_client_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        table_column_client_login.setCellValueFactory(new PropertyValueFactory<>("clientlogin"));
        table_column_client_phone_number.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        table_column_client_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table_column_room_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
