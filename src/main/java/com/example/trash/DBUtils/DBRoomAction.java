package com.example.trash.DBUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;
import static com.example.trash.DBUtils.OtherUtils.isEmptyFields;

public class DBRoomAction {
    public static void actionRoom(ActionEvent actionEvent, String id, String type, String status, String number, String operation) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckRoomExists = null;
        ResultSet resultSet = null;

        if (isEmptyFields(id, number)) return;

        try {
            connection = DBConnecting();
            psCheckRoomExists = connection.prepareStatement("SELECT * FROM rooms WHERE hotel_id = ? and number = ?");
            psCheckRoomExists.setInt(1, Integer.parseInt(id));
            psCheckRoomExists.setInt(2, Integer.parseInt(number));
            resultSet = psCheckRoomExists.executeQuery();

            switch (operation) {
                case "add" -> {
                    if (resultSet.isBeforeFirst()) {
                        System.out.println("Room already exists");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rechange data. U cant use that.");
                        alert.show();
                    } else {
                        psAction = connection.prepareStatement("INSERT INTO rooms (hotel_id, type, status, number) VALUES (?, ?, ?, ?)");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setString(2, type);
                        psAction.setString(3, status);
                        psAction.setInt(4, Integer.parseInt(number));
                        psAction.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Информация");
                        alert.setHeaderText(null);
                        alert.setContentText("Data has been updated");
                        alert.showAndWait();
                    }
                }
                case "delete" -> {
                    if (resultSet.isBeforeFirst()) {
                        psAction = connection.prepareStatement("DELETE FROM rooms WHERE hotel_id = ? AND number = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setInt(2, Integer.parseInt(number));
                        psAction.executeUpdate();
                        psAction = connection.prepareStatement("DELETE FROM booking_rooms WHERE id = ? AND roomnumber = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setInt(2, Integer.parseInt(number));
                        psAction.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Информация");
                        alert.setHeaderText(null);
                        alert.setContentText("Data has been updated");
                        alert.showAndWait();
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
