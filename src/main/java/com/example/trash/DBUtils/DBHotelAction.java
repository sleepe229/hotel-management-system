package com.example.trash.DBUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.trash.DBUtils.DBConnection.DBConnecting;
import static com.example.trash.DBUtils.OtherUtils.isEmptyField;

public class DBHotelAction {
    public static void actionHotel(ActionEvent actionEvent, String id, String name, String stars, String location, String operation) {
        Connection connection = null;
        PreparedStatement psAction = null;
        PreparedStatement psCheckHotelExists = null;
        ResultSet resultSet = null;

        if(isEmptyField(id)) return;

        try {
            connection = DBConnecting();
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
}
