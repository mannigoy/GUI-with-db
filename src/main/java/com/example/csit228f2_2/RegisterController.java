package com.example.csit228f2_2;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private Stage helloApplicationStage;

    public void setHelloApplicationStage(Stage stage) {
        this.helloApplicationStage = stage;
    }

    @FXML
    private void onRegisterButtonClick() {
        String pass1 =txtPassword.getText();
        String pass2= txtConfirmPassword.getText();
        String user=txtUsername.getText();

        if(confirm(pass1,pass2)){
            try(Connection c = MySQLConnection.getConnection();
                PreparedStatement statement = c.prepareStatement(
                        "INSERT INTO users (username, password) VALUES (?, ?)")){

                statement.setString(1, user);
                statement.setString(2, pass1);
                int rowsInserted = statement.executeUpdate();
                if(rowsInserted > 0){
                    System.out.println("Data inserted successfully");
                    Platform.runLater(() -> {
                        for (Stage stage : StageHelper.getStages()) {
                            stage.close();
                        }
                        // Restart the application
                        HelloApplication.restart();
                    });
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }
    private void resetHelloApplicationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Hello-view.fxml"));
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


    }}
