package com.example.trash.Controllers;

import com.example.trash.DBUtils.DBBookedRoomFinder;
import com.example.trash.DBUtils.DBBuyAcceptedRoom;
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

public class RoomBookingController implements Initializable {

    public TableView<Object> table_view_booked_number;
    public TableColumn<String, String> table_column_hotel_id;
    public TableColumn<String, String> table_column_room_number;
    public TableColumn<String, String> table_column_fullname;
    public TableColumn<String, String> table_column_phone_number;
    public TableColumn<String, String> table_column_email;
    public TableColumn<String, String> table_column_room_status;
    public Button button_buy_number;
    public TextField tf_room_number;
    public TextField tf_hotel_id;


    @FXML
    private Button button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable(new ActionEvent());
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OtherUtils.changeScene(actionEvent, "/com/example/trash/user-logged-in.fxml", "user", UserLoggedInController.LAST_USER_LOGIN, "user");
            }
        });
        button_buy_number.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBBuyAcceptedRoom.buyAcceptedRoom(actionEvent, tf_hotel_id.getText(), tf_room_number.getText());
            }
        });
    }
    private void reloadTable(ActionEvent actionEvent){
        table_view_booked_number.setItems(DBBookedRoomFinder.finderBookedRooms(actionEvent, UserLoggedInController.LAST_USER_LOGIN));
        table_column_hotel_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_column_room_number.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        table_column_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        table_column_phone_number.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        table_column_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table_column_room_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
