package com.example.agpsdesktopapp;

import javafx.scene.control.Alert;

import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/firedepartment";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "vfhc2015";

    public static void insertData(String fireDepartment, int ac, int anr, int al) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Создаем таблицу, если она не существует
            createTableIfNotExists(connection);

            // Проверяем, что не достигнуто ограничение в 5 записей
            if (getRowCount(connection) >= 5) {
                // Создаем всплывающее окно с сообщением
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Предупреждение");
                alert.setHeaderText("Достигнуто ограничение на ввод данных (максимум 5 записей).");
                alert.showAndWait();
                return;
            }

            // Вставляем данные в базу данных
            String insertQuery = "INSERT INTO fire_data (fire_department, ac, anr, al) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, fireDepartment);
                preparedStatement.setInt(2, ac);
                preparedStatement.setInt(3, anr);
                preparedStatement.setInt(4, al);
                preparedStatement.executeUpdate();
            }

            System.out.println("Данные успешно добавлены в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS fire_data (" +
                "id SERIAL PRIMARY KEY," +
                "fire_department VARCHAR(255)," +
                "ac INT," +
                "anr INT," +
                "al INT" +
                ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    private static int getRowCount(Connection connection) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM fire_data";
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
             var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    public static boolean searchFireDepartment(String query) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            query = query.toLowerCase();

            // Проверяем, соответствует ли запрос одному из допустимых вариантов
            if (query.equals("2") || query.equals("№2") || query.equals("пожарная часть №2")) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Если не найдено, возвращаем false
        return false;
    }

    static void resetDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM fire_data";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.executeUpdate();
            }
            System.out.println("База данных успешно очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static int getTotalSumFromDatabase(String... searchCriteria) {
        int totalSum = -1;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectQuery = "SELECT SUM(ac + anr + al) FROM fire_data WHERE fire_department IN (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                for (int i = 0; i < searchCriteria.length; i++) {
                    preparedStatement.setString(i + 1, searchCriteria[i]);
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        totalSum = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSum;
    }
}
