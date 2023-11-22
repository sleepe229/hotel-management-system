package com.example.agpsdesktopapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public Button button_sign_up;
    public TextField tf_login;
    public TextField tf_password;
    public Button button_log_in;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!tf_login.getText().trim().isEmpty() &&!tf_password.getText().trim().isEmpty()){
                    DBUtils.signUpUser(actionEvent, tf_login.getText(), tf_password.getText(), "user");
                } else{
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up");
                    alert.show();
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "hello-view.fxml", "Log in!", null, null);
            }
        });

    }
}