package com.example.trash.DBUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBBookingRoom {
    public static void bookRoom(ActionEvent actionEvent, String hotel_id, String room_number, String client_phone_number, String client_login, String client_fullname, String client_email) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckRoomExists = null;
        PreparedStatement psBookedRoom = null;
        ResultSet resultExistSet = null;
        ResultSet resultBookedSet = null;
        try {
            connection = DBConnecting();
            psBookedRoom = connection.prepareStatement("SELECT * FROM booking_rooms WHERE id = ? and roomnumber = ?");
            psBookedRoom.setInt(1, Integer.parseInt(hotel_id));
            psBookedRoom.setInt(2, Integer.parseInt(room_number));
            resultBookedSet = psBookedRoom.executeQuery();
            psCheckRoomExists = connection.prepareStatement("SELECT * FROM rooms WHERE hotel_id = ? and number = ?");
            psCheckRoomExists.setInt(1, Integer.parseInt(hotel_id));
            psCheckRoomExists.setInt(2, Integer.parseInt(room_number));
            resultExistSet = psCheckRoomExists.executeQuery();

            if (!resultExistSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Room not exist.");
                alert.show();
            } else if (resultBookedSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Room has been booked by other client.");
                alert.show();
            } else {
                psAction = connection.prepareStatement("INSERT INTO booking_rooms (id, roomnumber, fullname,  clientlogin, phonenumber, email, status) VALUES (?, ?, ?, ?, ?, ?, 'checking')");
                psAction.setInt(1, Integer.parseInt(hotel_id));
                psAction.setInt(2, Integer.parseInt(room_number));
                psAction.setString(3, client_fullname);
                psAction.setString(4, client_phone_number);
                psAction.setString(5, client_email);
                psAction.setString(6, client_login);
                psAction.executeUpdate();

                psAction = connection.prepareStatement("update rooms set status = 'ordered' where hotel_id = ? and number = ?");
                psAction.setInt(1, Integer.parseInt(hotel_id));
                psAction.setInt(2, Integer.parseInt(room_number));
                psAction.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Room has been booked. Check the reservation status in Your Rooms");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultExistSet != null) {
                try {
                    resultExistSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psBookedRoom != null) {
                try {
                    psBookedRoom.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultBookedSet != null) {
                try {
                    resultBookedSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckRoomExists != null) {
                try {
                    psCheckRoomExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psAction != null) {
                try {
                    psAction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
