package com.example.csit228f2_2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.MalformedURLException;
import java.sql.*;

import static com.example.csit228f2_2.HelloApplication.users;

public class UserViewController {
    @FXML
    Text txtName,txtBalance;
    @FXML
    TextField tfName;

    @FXML
    Button btnAdd;
    private int loggedInUserId;

    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }

    @FXML
    private void btnAddClick(){
        String name = tfName.getText();
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement pstmt = c.prepareStatement(
                     "INSERT INTO PersonalDetails (user_id, name) VALUES (?, ?)")) {
            // Set the values for the PreparedStatement
            pstmt.setInt(1, loggedInUserId);
            pstmt.setString(2, name);
            // Execute the query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the data was inserted successfully
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully for user ID " + loggedInUserId);
                btnAdd.setText("added");
            } else {
                System.out.println("Failed to insert data for user ID " + loggedInUserId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
