package com.example.trash;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBRoomsBookingUtils{
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Hotel";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "vfhc2015";

    public static void buyAcceptedRoom(ActionEvent actionEvent, String id, String status) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckHotelExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            psCheckHotelExists = connection.prepareStatement("SELECT * FROM booking_rooms WHERE id = ? and status = ?");
            psCheckHotelExists.setInt(1, Integer.parseInt(id));
            psCheckHotelExists.setString(2, status);
            resultSet = psCheckHotelExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Hotel already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Rechange data. U cant use that.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("update booking_rooms set status = 'bought' where id = ?, and status = ?)");
                psInsert.setInt(1, Integer.parseInt(id));
                psInsert.setString(2, status);
                psInsert.executeUpdate();

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

    public static ObservableList<Object> founderBookedRooms(ActionEvent actionEvent, String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> BookedRoomsList = FXCollections.observableArrayList();
        try {
            if(login != null) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                preparedStatement = connection.prepareStatement("SELECT * FROM booking_rooms WHERE login = ?");
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                BookedRoomsList.clear();
            }else{
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                preparedStatement = connection.prepareStatement("SELECT * FROM booking_rooms");
                resultSet = preparedStatement.executeQuery();
                BookedRoomsList.clear();
            }
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Login not founded in the DB");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BookedRoomsList;
    }

}