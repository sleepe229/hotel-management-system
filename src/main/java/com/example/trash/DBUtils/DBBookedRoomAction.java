package com.example.trash.DBUtils;

import com.example.trash.Controllers.UserLoggedInController;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBBookedRoomAction {
    public static void actionBookingRoom(ActionEvent actionEvent, String id, String number, String userlogin, String operation) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckRoomExists = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnecting();
            psCheckRoomExists = connection.prepareStatement("SELECT * FROM booking_rooms WHERE id = ? and roomnumber = ?");
            psCheckRoomExists.setInt(1, Integer.parseInt(id));
            psCheckRoomExists.setInt(2, Integer.parseInt(number));
            resultSet = psCheckRoomExists.executeQuery();

            switch (operation) {
                case "accept" -> {
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("Room already not exists");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rechange data. You cant use that.");
                        alert.show();
                    } else {
                        psAction = connection.prepareStatement("update booking_rooms set status = 'ready_to_buy' where id = ? and roomnumber = ? and clientlogin = ? and status = 'checking'");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setInt(2, Integer.parseInt(number));
                        psAction.setString(3, userlogin);
                        psAction.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Data has been updated");
                        alert.show();
                    }
                }
                case "reject" -> {
                    if (resultSet.isBeforeFirst()) {
                        psAction = connection.prepareStatement("delete from booking_rooms where id = ? and roomnumber = ? and clientlogin = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setInt(2, Integer.parseInt(number));
                        psAction.setString(3, userlogin);
                        psAction.executeUpdate();
                        psAction = connection.prepareStatement("update rooms set status = 'free' where hotel_id = ? and number = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setInt(2, Integer.parseInt(number));
                        psAction.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Data has been updated");
                        alert.show();
                    } else {
                        System.out.println("Room already not exists");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rechange data. U cant use that.");
                        alert.show();
                    }
                }
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
