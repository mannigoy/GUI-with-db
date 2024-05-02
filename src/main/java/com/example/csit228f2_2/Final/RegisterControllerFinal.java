package com.example.csit228f2_2.Final;

import com.example.csit228f2_2.MySQLConnection;
import com.example.csit228f2_2.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;

public class RegisterControllerFinal {
    @FXML
    ImageView logo;
    @FXML
    private AnchorPane root;

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnRegister;
    @FXML
    PasswordField txtConfirmPassword;
    @FXML
    Text altText,altTextFinal;
    @FXML
    Hyperlink forgotPassword;



    @FXML
    public void initialize(){
        Image image =new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);

    }
    @FXML
    private void onRegisterButtonClick() {
        altText.setFill(Color.RED);
        altTextFinal.setFill(Color.RED);
        String pass1 = txtPassword.getText();
        String pass2 = txtConfirmPassword.getText();
        Main.EnteredUsername= txtUsername.getText();
        if (confirm(pass1, pass2)) {
            try (Connection c = MySQLConnection.getConnection()) {

                try (PreparedStatement checkStatement = c.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
                    checkStatement.setString(1, Main.EnteredUsername);
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        if (resultSet.next() && resultSet.getInt(1) > 0) {
                            altTextFinal.setText("Username already exists.");

                            return;
                        }
                    }
                }

                // If username is unique, proceed to insert the data
                try (PreparedStatement insertStatement = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
                    insertStatement.setString(1, Main.EnteredUsername);
                    insertStatement.setString(2, pass1);
                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0) {
                      //  ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                    //    if (generatedKeys.next()) {
                     //        Main.userId = generatedKeys.getInt(1); // Retrieve the generated user ID

                   //     }

                        System.out.println("Data inserted successfully");
                        showUserWindow();
                        Stage stage = (Stage) btnRegister.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        else
            altTextFinal.setText("Password Not the same");
    }
    private void showUserWindow() {
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
    @FXML
    public boolean confirm(String pass1, String pass2){

        return pass1.equals(pass2);


    }
    @FXML
    private void onLogInClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage currentStage = (Stage) btnRegister.getScene().getWindow();
        currentStage.close();
    }
}
