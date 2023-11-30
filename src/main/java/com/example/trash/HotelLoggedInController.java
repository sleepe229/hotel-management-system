package com.example.trash;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        button_reload_table.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBRoomsBookingUtils.founderBookedRooms(actionEvent, null);
                table_column_hotel_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                table_column_room_number.setCellValueFactory(new PropertyValueFactory<>("number"));
                table_column_client_fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
                table_column_client_login.setCellValueFactory(new PropertyValueFactory<>("clientlogin"));
                table_column_client_phone_number.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
                table_column_client_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            }
        });
        button_accept_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        button_reject_room.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });
    }

}
