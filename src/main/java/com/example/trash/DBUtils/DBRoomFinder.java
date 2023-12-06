package com.example.trash.DBUtils;

import com.example.trash.Models.RoomDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBRoomFinder {
    public static ObservableList<Object> finderRooms(ActionEvent actionEvent, String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> RoomsList = FXCollections.observableArrayList();
        try {
            connection = DBConnecting();
            preparedStatement = connection.prepareStatement("SELECT * FROM rooms WHERE hotel_id = ? and status = 'free'");
            preparedStatement.setInt(1, Integer.parseInt(id));
            resultSet = preparedStatement.executeQuery();
            RoomsList.clear();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Hotel not founded in the DB");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    RoomsList.add(new RoomDTO(
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("type"),
                        resultSet.getString("status"),
                        resultSet.getInt("number")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RoomsList;
    }
}
