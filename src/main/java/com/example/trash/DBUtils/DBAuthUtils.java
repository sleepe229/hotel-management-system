package com.example.trash.DBUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBAuthUtils {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Hotel";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "vfhc2015";
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String login, String status){
        Parent root = null;

        if (login != null && status != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBAuthUtils.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try{
                root = FXMLLoader.load(DBAuthUtils.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void signUpUser(ActionEvent actionEvent, String login, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        if (isEmptyFields(login, password)){
            return;
        }

        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
    public static void logInUser(ActionEvent actionEvent, String login, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (isEmptyFields(login, password)){
            return;
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
    static boolean isEmptyFields(String login, String password){
        if (login.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Fields must be filled in");
            alert.setContentText("Enter login and password");
            alert.showAndWait();
            return true;
        }
        return false;
    }
}
