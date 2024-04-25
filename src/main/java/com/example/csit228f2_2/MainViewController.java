package com.example.csit228f2_2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {
    @FXML
    TableView tableAdmin;
    @FXML
    private TableColumn<User, Integer> colId;
    @FXML
    private TableColumn<User, String> colUser;
    @FXML
    private TableColumn<User, String> colPassword;
    @FXML
    private Button deleteButton;
    @FXML
    Button btnSignOut;

    private List<User> users = new ArrayList<>();




    public void initialize() {
        // Set cell value factories to populate table columns
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));


        // Load data from database
        getFromTable();
    }

    public void getFromTable() {
        try (Connection connection = MySQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM USERS";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                System.out.println(id);
                String name = resultSet.getString("username");
                String email = resultSet.getString("password");
               users.add(new User(id,name, email));
            }
            // Add data to TableView
            tableAdmin.getItems().addAll(users);
        } catch (SQLException | MalformedURLException e ) {
            throw new RuntimeException(e);

        }
    }
    @FXML
    private void deleteSelectedRow() {
        User selectedUser = (User) tableAdmin.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Remove the selected user from the table
            tableAdmin.getItems().remove(selectedUser);
            // You can also delete the user from the database here if needed
        }
        try (Connection connection = MySQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM USERS WHERE id = " + selectedUser.getId();
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

@FXML
    private void handleSignOut() {
        try {
            // Load the login scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            Parent root = loader.load();

            // Get the stage from the button's scene and set the login scene
            Stage stage = (Stage) btnSignOut.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
    }

