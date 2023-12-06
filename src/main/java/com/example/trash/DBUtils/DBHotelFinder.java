package com.example.trash.DBUtils;

import com.example.trash.Models.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBHotelFinder {
    public static ObservableList<Object> finderHotels(ActionEvent actionEvent) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> HotelList = FXCollections.observableArrayList();
        try {
            connection = DBConnecting();
            preparedStatement = connection.prepareStatement("SELECT * FROM hotels");
            resultSet = preparedStatement.executeQuery();
            HotelList.clear();

            while (resultSet.next()) {
                HotelList.add(new Hotel(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("stars"),
                    resultSet.getString("location")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HotelList;
    }
}
