package com.example.trash.DBUtils;

import com.example.trash.Models.Room;
import com.example.trash.Controllers.UserLoggedInController;
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

    public static void actionBookingRoom(ActionEvent actionEvent, String id, String number, String userlogin, String operation) {
        Connection connection = null;
        PreparedStatement psAction = null;
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
                        alert.setContentText("Rechange data. You cant use that.");
                        alert.show();
                    } else {
                        psAction = connection.prepareStatement("update booking_rooms set status = 'ready_to_buy' where id = ? and roomnumber = ? and clientlogin = ?");
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
                        psAction = connection.prepareStatement("delete from booking_rooms where id = ? and number = ? and clientlogin = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setInt(2, Integer.parseInt(number));
                        psAction.setString(3, UserLoggedInController.LAST_USER_LOGIN);
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