package com.example.trash.DBUtils;

import com.example.trash.Models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBBookedRoomFinder {
    public static ObservableList<Object> finderBookedRooms(ActionEvent actionEvent, String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> BookedRoomsList = FXCollections.observableArrayList();
        try {
            if(login != null) {
                connection = DBConnecting();
                preparedStatement = connection.prepareStatement("SELECT * FROM booking_rooms WHERE clientlogin = ?");
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                BookedRoomsList.clear();
            }else {
                connection = DBConnecting();
                preparedStatement = connection.prepareStatement("SELECT * FROM booking_rooms");
                resultSet = preparedStatement.executeQuery();
                BookedRoomsList.clear();
            }
            while (resultSet.next()) {
                BookedRoomsList.add(new Room(
                        resultSet.getInt("id"),
                        resultSet.getInt("roomnumber"),
                        resultSet.getString("fullname"),
                        resultSet.getString("clientlogin"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("email"),
                        resultSet.getString("status")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BookedRoomsList;
    }
}
