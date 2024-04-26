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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public static int userId;

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

    String EnteredUsername, EnteredPassword;

    @FXML
    public void initialize(){
        Image image =new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);

    }
    public void LogInClick() {

        EnteredUsername = LogIn_txtUsername.getText();
        EnteredPassword = LogIn_PF_password.getText();
        if(EnteredUsername.equals("admin") && EnteredPassword.equals("123test")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) LogIn_btnLogIn.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        else {
            //  userId = authenticateUser(EnteredUsername, EnteredPassword);
            try (Connection connection = MySQLConnection.getConnection();) {
                Statement statement = connection.createStatement();
                String selectQuery = "SELECT * FROM users WHERE username = '" + EnteredUsername + "' AND password = '" + EnteredPassword+"'";
                ResultSet resultSet = statement.executeQuery(selectQuery);

                if (resultSet.next()) {
                    userId = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String password = resultSet.getString("password");
                   // users.add(new User(userId, name, password));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage) LogIn_btnLogIn.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }


            } catch (SQLException | MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        altText.setText("Invalid username/password");

    }
    public void ForgotPassClicked(){
        EnteredUsername = LogIn_txtUsername.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePassword2.fxml"));
            Scene scene = new Scene(loader.load());
            ChangePassword changePassword = loader.getController();
            changePassword.setInitialUsername(EnteredUsername);

            Stage stage = (Stage)LogIn_btnLogIn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
