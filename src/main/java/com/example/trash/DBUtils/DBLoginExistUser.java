package com.example.trash.DBUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

import static com.example.trash.DBUtils.OtherUtils.changeScene;
import static com.example.trash.DBUtils.OtherUtils.isEmptyFields;
import static com.example.trash.DBUtils.DBConnection.DBConnecting;

public class DBLoginExistUser {
    public static void logInUser(ActionEvent actionEvent, String login, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (isEmptyFields(login, password)){
            return;
        }
        try {
            connection = DBConnecting();
            preparedStatement = connection.prepareStatement("SELECT password, status FROM accounts WHERE login = ?");
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("User not founded in the DB");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedStatus = resultSet.getString("status");
                    if (retrievedPassword.equals(password)){
                        switch (retrievedStatus){
                            case "user" -> changeScene(actionEvent, "/com/example/trash/user-logged-in.fxml", "user", login, retrievedStatus);
                            case "hotel" -> changeScene(actionEvent, "/com/example/trash/hotel-logged-in.fxml", "hotel", login, retrievedStatus);
                            case "admin" -> changeScene(actionEvent, "/com/example/trash/admin-logged-in.fxml", "admin", login, retrievedStatus);
                        }
                    }else{
                        System.out.println("Password");
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
