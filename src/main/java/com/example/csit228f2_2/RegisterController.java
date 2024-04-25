package com.example.csit228f2_2;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnRegister;
    @FXML
    PasswordField txtConfirmPassword;
    @FXML
    Text altText;
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private Stage helloApplicationStage;



    @FXML
    private void onRegisterButtonClick() {
        String pass1 = txtPassword.getText();
        String pass2 = txtConfirmPassword.getText();
        String user = txtUsername.getText();
        if (confirm(pass1, pass2)) {
            try (Connection c = MySQLConnection.getConnection()) {

                try (PreparedStatement checkStatement = c.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
                    checkStatement.setString(1, user);
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        if (resultSet.next() && resultSet.getInt(1) > 0) {
                            altText.setText("Username already exists.");

                            return;
                        }
                    }
                }

                // If username is unique, proceed to insert the data
                try (PreparedStatement insertStatement = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
                    insertStatement.setString(1, user);
                    insertStatement.setString(2, pass1);
                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Data inserted successfully");
                        showSignInWindow();
                        Stage stage = (Stage) btnRegister.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        else
            altText.setText("Password Not the same");
    }

    private void showSignInWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setInitialUsername(String username) {
        txtUsername.setText(username); // Set the passed username in the text field
    }




    @FXML
    public boolean confirm(String pass1, String pass2){

        return pass1.equals(pass2);


    }}
