package com.example.trash;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable{
    @FXML
    public Button button_sign_up;
    @FXML
    private Button button_login;

    @FXML
    private TextField tf_login;
    @FXML
    private PasswordField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UserLoggedInController.LAST_USER_LOGIN = tf_login.getText();
                DBAuthUtils.logInUser(actionEvent, tf_login.getText(), tf_password.getText());
            }
        });
        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBAuthUtils.changeScene(actionEvent, "sign-up.fxml", "Sign Up!", null, null);
            }
        });
    }
}
