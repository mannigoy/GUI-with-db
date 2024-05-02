package com.example.csit228f2_2.Final;

import com.example.csit228f2_2.ChangePassController;
import com.example.csit228f2_2.MySQLConnection;
import com.example.csit228f2_2.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;

public class LoginController implements FXMLLOADER{



    @FXML
    ImageView logo;
    @FXML
    Button LogIn_btnLogIn;

    @FXML
    TextField LogIn_txtUsername;
    @FXML
    PasswordField LogIn_PF_password;
    @FXML
    Text altText;
    @FXML
    Hyperlink forgotPassword;




    @FXML
    public void initialize(){
        Image image =new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);

    }
    public void LogInClick() {

        Main.EnteredUsername = LogIn_txtUsername.getText();
        Main.EnteredPassword = LogIn_PF_password.getText();
        if(Main.EnteredUsername.equals("admin") && Main.EnteredPassword.equals("123test")) {
            loadFxml("MainView.fxml");
        }
        else {
            //  userId = authenticateUser(EnteredUsername, EnteredPassword);
            try (Connection connection = MySQLConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
                statement.setString(1, Main.EnteredUsername);
                statement.setString(2, Main.EnteredPassword);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String passwordFromDB = resultSet.getString("password");
                    Main.userId = resultSet.getInt("id");
                    if (Main.EnteredPassword.equals(passwordFromDB)) {
                        Main.userId = resultSet.getInt("id");
                        loadFxml("UserView.fxml");
                        Stage currentStage = (Stage) LogIn_btnLogIn.getScene().getWindow();
                        currentStage.close();
                        return;
                    } else {
                        altText.setText("Invalid password");
                    }
                } else {
                    altText.setText("Invalid username");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void ForgotPassClicked(){
        Main.EnteredUsername = LogIn_txtUsername.getText();
       loadFxml("ChangePassword2.fxml");
        Stage currentStage = (Stage) LogIn_btnLogIn.getScene().getWindow();
        currentStage.close();
    }
}
