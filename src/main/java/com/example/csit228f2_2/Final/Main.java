package com.example.csit228f2_2.Final;

import com.example.csit228f2_2.MySQLConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        createTable();
        createPersonalDetailsTableInNewDatabase();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

    }


    public void createTable(){
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()){
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL)";
            statement.execute(createTableQuery);
            System.out.println("Table created successfully");
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void createPersonalDetailsTableInNewDatabase() {
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {

            // Create PersonalDetails table
            String createTableQuery = "CREATE TABLE IF NOT EXISTS PersonalDetails (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "name VARCHAR(80) NOT NULL," +
                    "money INT DEFAULT 0," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))";
            statement.execute(createTableQuery);

            // Check for existing user_id values in users table
            ResultSet resultSet = statement.executeQuery("SELECT id FROM users");
            Set<Integer> userIds = new HashSet<>();
            while (resultSet.next()) {
                userIds.add(resultSet.getInt("id"));
            }

            // Insert sample data into PersonalDetails table
            if (!userIds.isEmpty()) {
                for (int userId : userIds) {
                    String insertQuery = "INSERT INTO PersonalDetails (user_id, name) VALUES (?, ?)";
                    try (PreparedStatement preparedStatement = c.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, userId);
                        preparedStatement.setString(2, "Sample Name");
                        preparedStatement.executeUpdate();
                    }
                }
                System.out.println("Sample data inserted into PersonalDetails table.");
            } else {
                System.out.println("No existing user data found. Skipping insertion into PersonalDetails table.");
            }

            System.out.println("PersonalDetails table created successfully in the SecondDatabase");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
