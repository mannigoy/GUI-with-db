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
            String username = "manni";
            String password = "123456789";
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
}
