package com.example.trash.DBUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

import static com.example.trash.DBUtils.OtherUtils.changeScene;
import static com.example.trash.DBUtils.OtherUtils.isEmptyFields;
import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBSignUpClient {
    public static void signUpUser(ActionEvent actionEvent, String login, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        if (isEmptyFields(login, password)){
            return;
        }

        try{
            connection = DBConnecting();
            psCheckUserExists = connection.prepareStatement("SELECT * FROM accounts WHERE login = ?");
            psCheckUserExists.setString(1, login);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Rechange username. U cant use that.");
                alert.show();
            } else{
                psInsert = connection.prepareStatement("INSERT INTO accounts (login, password, status) VALUES (?, ?, ?)");
                psInsert.setString(1, login);
                psInsert.setString(2, password);
                psInsert.setString(3, "user");
                psInsert.executeUpdate();

                changeScene(actionEvent, "user-logged-in.fxml", "xexexeex", login, "user");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try{
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
