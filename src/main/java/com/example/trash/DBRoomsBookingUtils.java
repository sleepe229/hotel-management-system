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

    public static void buyAcceptedRoom(ActionEvent actionEvent, String id, String roomnumber) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckHotelExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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

    public static ObservableList<Object> founderBookedRooms(ActionEvent actionEvent, String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> BookedRoomsList = FXCollections.observableArrayList();
        try {
            if(login != null) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                preparedStatement = connection.prepareStatement("SELECT * FROM booking_rooms WHERE clientlogin = ?");
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                BookedRoomsList.clear();
            }else {
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

    public static void actionBookingRoom(ActionEvent actionEvent, String id, String number, String userlogin, String operation) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckRoomExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            psCheckRoomExists = connection.prepareStatement("SELECT * FROM booking_rooms WHERE id = ? and status = 'checking'");
            psCheckRoomExists.setInt(1, Integer.parseInt(id));
            resultSet = psCheckRoomExists.executeQuery();

            switch (operation) {
                case "accept" -> {
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("Room already not exists");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rechange data. U cant use that.");
                        alert.show();
                    } else {
                        psInsert = connection.prepareStatement("update booking_rooms set status = 'ready_to_buy' where id = ? and roomnumber = ? and clientlogin = ?");
                        psInsert.setInt(1, Integer.parseInt(id));
                        psInsert.setInt(2, Integer.parseInt(number));
                        psInsert.setString(3, userlogin);
                        psInsert.executeUpdate();
                    }
                }
                case "reject" -> {
                    if (resultSet.isBeforeFirst()) {
                        psInsert = connection.prepareStatement("delete from booking_rooms where id = ? and number = ? and clientlogin = ?");
                        psInsert.setInt(1, Integer.parseInt(id));
                        psInsert.setInt(2, Integer.parseInt(number));
                        psInsert.setString(3, UserLoggedInController.LAST_USER_LOGIN);
                        psInsert.executeUpdate();
                        psInsert = connection.prepareStatement("update rooms set status = 'free' where hotel_id = ? and number = ?");
                        psInsert.setInt(1, Integer.parseInt(id));
                        psInsert.setInt(2, Integer.parseInt(number));
                        psInsert.executeUpdate();
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