package com.example.trash.DBUtils;

import com.example.trash.Models.Hotel;
import com.example.trash.Models.RoomDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBHotelUtils {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Hotel";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "vfhc2015";

    public static void actionHotel(ActionEvent actionEvent, String id, String name, String stars, String location, String operation) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckHotelExists = null;
        ResultSet resultSet = null;

        if(isEmptyField(id)) return;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            psCheckHotelExists = connection.prepareStatement("SELECT * FROM hotels WHERE id = ?");
            psCheckHotelExists.setInt(1, Integer.parseInt(id));
            resultSet = psCheckHotelExists.executeQuery();
            switch (operation) {
                case "add" -> {
                    if (resultSet.isBeforeFirst()) {
                        System.out.println("Hotel already exists");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rechange data. You cant use that.");
                        alert.show();
                    } else {
                        psAction = connection.prepareStatement("INSERT INTO hotels (id, name, stars, location) VALUES (?, ?, ?, ?)");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.setString(2, name);
                        psAction.setInt(3, Integer.parseInt(stars));
                        psAction.setString(4, location);
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
                        psAction = connection.prepareStatement("DELETE FROM hotels WHERE id = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.executeUpdate();
                        psAction = connection.prepareStatement("DELETE FROM booking_rooms WHERE id = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.executeUpdate();
                        psAction = connection.prepareStatement("DELETE FROM rooms WHERE hotel_id = ?");
                        psAction.setInt(1, Integer.parseInt(id));
                        psAction.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Информация");
                        alert.setHeaderText(null);
                        alert.setContentText("Data has been updated");
                        alert.showAndWait();
                    } else {
                        System.out.println("Hotel ain't exists");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Rechange data. You cant use that.");
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
            if (psCheckHotelExists != null) {
                try {
                    psCheckHotelExists.close();
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

    public static void actionRoom(ActionEvent actionEvent, String id, String type, String status, String number, String operation) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckRoomExists = null;
        ResultSet resultSet = null;

        if (isEmptyFields(id, number)) return;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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

    public static ObservableList<Object> founderRooms(ActionEvent actionEvent, String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> RoomsList = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
    public static ObservableList<Object> founderHotels(ActionEvent actionEvent) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Object> HotelList = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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

    public static void bookRoom(ActionEvent actionEvent, String hotel_id, String room_number, String client_phone_number, String client_login, String client_fullname, String client_email) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckRoomExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            psCheckRoomExists = connection.prepareStatement("SELECT * FROM booking_rooms WHERE id = ? and roomnumber = ?");
            psCheckRoomExists.setInt(1, Integer.parseInt(hotel_id));
            psCheckRoomExists.setInt(2, Integer.parseInt(room_number));
            resultSet = psCheckRoomExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
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
    static boolean isEmptyFields(String id, String roomnumber){
        if (id.isEmpty() || roomnumber.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Fields must be filled in");
            alert.setContentText("Enter login and password");
            alert.showAndWait();
            return true;
        }
        return false;
    }
    public static boolean isEmptyField(String id){
        if (id.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Field must be filled in");
            alert.showAndWait();
            return true;
        }
        return false;
    }
}
