package com.example.agpsdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.agpsdesktopapp.DatabaseHandler.getTotalSumFromDatabase;
import static com.example.agpsdesktopapp.DatabaseHandler.searchFireDepartment;

public class HelloController {

    @FXML
    private TextField numberFireDepartmentTextField;

    @FXML
    private TextField numberACTextField;

    @FXML
    private TextField numberANRTextField;

    @FXML
    private TextField numberALTextField;

    @FXML
    private Label resultLabel;


    @FXML
    private void enterData() {
        String fireDepartment = numberFireDepartmentTextField.getText();
        String acText = numberACTextField.getText();
        String anrText = numberANRTextField.getText();
        String alText = numberALTextField.getText();

        if (acText.isEmpty() || anrText.isEmpty() || alText.isEmpty() ||
                !isNaturalNumber(acText) || !isNaturalNumber(anrText) || !isNaturalNumber(alText)) {
            showError("Поля АЦ, АНР и АЛ должны быть заполнены нарутальными числами.");
            return;
        }

        int ac = Integer.parseInt(acText);
        int anr = Integer.parseInt(anrText);
        int al = Integer.parseInt(alText);

        DatabaseHandler.insertData(fireDepartment, ac, anr, al);
    }


    private boolean isNaturalNumber(String str) {
        try {
            int number = Integer.parseInt(str);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void showResult() {
        int totalSum = getTotalSumFromDatabase("2", "№2", "пожарная часть №2");

        if (totalSum >= 0) {
            showInfo("Количество техники в пожарной части №2: " + totalSum);
        } else {
            showInfo("Пожарной части не найдено в базе данных.");
        }
    }


    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void resetData() {
        // Создаем всплывающее окно с подтверждением
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите сбросить данные в базе данных?");
        alert.setContentText("Сброс данных удалит все записи в базе данных.");

        // Ожидаем ответ пользователя
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Если пользователь подтвердил сброс, выполните сброс данных в базе данных
            DatabaseHandler.resetDatabase();
            // Очистите текстовые поля и метку результатов
            numberFireDepartmentTextField.clear();
            numberACTextField.clear();
            numberANRTextField.clear();
            numberALTextField.clear();
            resultLabel.setText("");
        }
    }

    @FXML
    private void exit() {
        Stage stage = (Stage) resultLabel.getScene().getWindow();
        stage.close();
    }
}
