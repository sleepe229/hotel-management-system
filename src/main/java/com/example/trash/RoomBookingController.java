package com.example.trash;

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
    public TableColumn<String, String> table_column_client_login;
    public TableColumn<String, String> table_column_phone_number;
    public TableColumn<String, String> table_column_email;
    public TableColumn<String, String> table_column_room_status;
    public TextField tf_room_founder_id;
    public Button button_buy_number;

    @FXML
    private Button button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        reloadTable(new ActionEvent());

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBAuthUtils.changeScene(actionEvent, "hello-view.fxml", "Log in!", null, null);
            }
        });
        button_buy_number.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });
    }
    private void reloadTable(ActionEvent actionEvent){
        table_column_hotel_id.setText("id");
        table_column_room_number.setText("room number");
        table_column_fullname.setText("fullname");
        table_column_client_login.setText("login");
        table_column_phone_number.setText("number");
        table_column_email.setText("email");
        table_column_room_status.setText("room status");
        table_view_booked_number.setItems(DBHotelUtils.founderRooms(actionEvent, tf_room_founder_id.getText()));
        table_column_hotel_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_column_room_number.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        table_column_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        table_column_client_login.setCellValueFactory(new PropertyValueFactory<>( "clientlogin"));
        table_column_phone_number.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        table_column_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table_column_room_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
