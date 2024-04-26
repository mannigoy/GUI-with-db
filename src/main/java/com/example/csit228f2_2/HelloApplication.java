    package com.example.csit228f2_2;

    import javafx.application.Application;

    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;

    import javafx.scene.Scene;
    import javafx.scene.control.*;

    import javafx.scene.text.Text;
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.net.MalformedURLException;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    public class HelloApplication extends Application {
        @FXML
        Button LogIn_btnLogIn;
        @FXML
        Button LogIn_btnRegister;
        @FXML
        TextField LogIn_txtUsername;
        @FXML
        PasswordField LogIn_PF_password;
        @FXML
        Text altText;
        @FXML
        Hyperlink forgotPassword;

        String EnteredUsername, EnteredPassword;

        public static List<User> users;
        private static HelloApplication instance;

        public static HelloApplication getInstance() {
            return instance;
        }
        public static void main(String[] args) {
            launch();
        }
        public static int userId;
        @Override
        public void start(Stage stage) throws Exception {
            createTable();
            createPersonalDetailsTableInNewDatabase();
            users = new ArrayList<>();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
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
                            users.add(new User(userId, name, password));
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserViewdimao.fxml"));
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


    public void RegisterClick(){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)LogIn_btnRegister.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ForgotPassClicked(){
            EnteredUsername = LogIn_txtUsername.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
            Scene scene = new Scene(loader.load());
            ChangePassController changePasswordController = loader.getController();
            changePasswordController.setInitialUsername(EnteredUsername);

            Stage stage = (Stage)LogIn_btnRegister.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
                String createTableQuery = "CREATE TABLE IF NOT EXISTS PersonalDetails (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "user_id INT," +
                        "name VARCHAR(80) NOT NULL," +
                        "money INT DEFAULT 0," +
                        "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (user_id) REFERENCES users(id))";
                statement.execute(createTableQuery);

                // Check for existing user_id values in users table
                ResultSet resultSet = statement.executeQuery("SELECT id FROM users");
                Set<Integer> userIds = new HashSet<>();
                while (resultSet.next()) {
                    userIds.add(resultSet.getInt("id"));
                }

                // Insert sample data into PersonalDetails table
                if (!userIds.isEmpty()) {
                    for (int userId : userIds) {
                        String insertQuery = "INSERT INTO PersonalDetails (user_id, name) VALUES (?, ?)";
                        try (PreparedStatement preparedStatement = c.prepareStatement(insertQuery)) {
                            preparedStatement.setInt(1, userId);
                            preparedStatement.setString(2, "Sample Name");
                            preparedStatement.executeUpdate();
                        }
                    }
                    System.out.println("Sample data inserted into PersonalDetails table.");
                } else {
                    System.out.println("No existing user data found. Skipping insertion into PersonalDetails table.");
                }

                System.out.println("PersonalDetails table created successfully in the SecondDatabase");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public void getFromTable(){
            try (Connection connection = MySQLConnection.getConnection();) {
                Statement statement = connection.createStatement();
                String selectQuery = "SELECT * FROM USERS";
                ResultSet resultSet = statement.executeQuery(selectQuery);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("password");
                    users.add(new User(id, name, email));
                }

            } catch (SQLException | MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }