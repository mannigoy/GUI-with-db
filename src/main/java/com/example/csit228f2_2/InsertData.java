package com.example.csit228f2_2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)")){
            String username = "admin";
            String password = "123test";
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("Data inserted successfully");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void insertDataIntoPersonalDetails(int userId, String name, String lastName, int money) {
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement pstmt = c.prepareStatement(
                     "INSERT INTO PersonalDetails (user_id, name, lastname, money) VALUES (?, ?, ?, ?)")) {

            // Set the values for the PreparedStatement
            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, lastName);
            pstmt.setInt(4, money);

            // Execute the query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the data was inserted successfully
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully for user ID " + userId);
            } else {
                System.out.println("Failed to insert data for user ID " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
