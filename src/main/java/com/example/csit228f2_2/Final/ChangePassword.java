package com.example.csit228f2_2.Final;

import com.example.csit228f2_2.MySQLConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePassword {
    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnRegister;
    @FXML
    PasswordField txtConfirmPassword;
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private Stage helloApplicationStage;

    @FXML
    ImageView logo;

    @FXML
    public void initialize(){
        Image image =new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);

    }

    @FXML
    private void onChangePassButtonClick() {
        String pass1 = txtPassword.getText();
        String pass2 = txtConfirmPassword.getText();
        String user = txtUsername.getText();

        if (confirm(pass1, pass2)) {
            try (Connection c = MySQLConnection.getConnection();
                 PreparedStatement checkStatement = c.prepareStatement("SELECT * FROM users WHERE username = ?")) {

                checkStatement.setString(1, user);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        try (PreparedStatement updateStatement = c.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {
                            updateStatement.setString(1, pass1);
                            updateStatement.setString(2, user);
                            int rowsUpdated = updateStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                // System.out.println("Password updated successfully");
                                showSignInWindow();
                                Stage stage = (Stage) btnRegister.getScene().getWindow();
                                stage.close();
                            }
                        }
                    } else {
                        // Username does not exist, print a message
                        System.out.println("Username not found. Please register first.");
                    }
                }
           }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showSignInWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
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


    }
}
