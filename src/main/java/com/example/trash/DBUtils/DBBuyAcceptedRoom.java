package com.example.trash.DBUtils;

import com.example.trash.Controllers.UserLoggedInController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBBuyAcceptedRoom {
    public static void buyAcceptedRoom(ActionEvent actionEvent, String id, String roomnumber) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckHotelExists = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnecting();
            psCheckHotelExists = connection.prepareStatement("SELECT * FROM booking_rooms WHERE id = ? and roomnumber = ? and clientlogin = ?");
            psCheckHotelExists.setInt(1, Integer.parseInt(id));
            psCheckHotelExists.setInt(2, Integer.parseInt(roomnumber));
            psCheckHotelExists.setString(3, UserLoggedInController.LAST_USER_LOGIN);
            resultSet = psCheckHotelExists.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Room not booked");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("That room aint booked with u.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("update booking_rooms set status = 'bought' where id = ? and roomnumber = ?");
                psInsert.setInt(1, Integer.parseInt(id));
                psInsert.setInt(2, Integer.parseInt(roomnumber));
                psInsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("U bought the room");
                alert.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckHotelExists != null) {
                try {
                    psCheckHotelExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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
