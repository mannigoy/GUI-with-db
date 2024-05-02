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
    public static String EnteredUsername, EnteredPassword;
    public static int userId;
    @Override
    public void start(Stage stage) throws Exception {
        createTable();
        createPersonalDetailsTableInNewDatabase();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
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
            String createTableQuery = "CREATE TABLE IF NOT EXISTS MoneyDataBase (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "type VARCHAR(50),"  +
                    "money DOUBLE DEFAULT 0," +
                    "category VARCHAR(50)," +
                    "description VARCHAR(80)," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))";
            statement.execute(createTableQuery);

            System.out.println("Second table created successfully in the Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
