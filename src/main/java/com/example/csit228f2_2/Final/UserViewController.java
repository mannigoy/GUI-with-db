package com.example.csit228f2_2.Final;

import com.example.csit228f2_2.MySQLConnection;
import com.example.csit228f2_2.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserViewController implements FXMLLOADER {
    @FXML
    ImageView logo;

    @FXML
    Button btnAdd, btnSignOut, btnDelete, btnEdit;
    @FXML
    Text txtUsername;
    @FXML
    TableView tableContents;
    @FXML
    BorderPane ablo;


    @FXML
    private TableColumn<Personal, String> colDate;
    @FXML
    private TableColumn<Personal, String> colType;
    @FXML
    private TableColumn<Personal, String> colAmount;
    @FXML
    private TableColumn<Personal, String> ColCategory;
    @FXML
    private TableColumn<Personal, String> colDescription;


    @FXML
    public void initialize() {
        Image image = new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);
        txtUsername.setText(Main.EnteredUsername);

        getFromTable();
    }

    @FXML
    public void onButtonAddClick() {
        loadFxml("Add_Money.fxml");
        Stage currentStage = (Stage) btnAdd.getScene().getWindow();
        currentStage.close();
    }


    public void getFromTable() {
        List<Personal> transactions = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM MONEYDATABASE where user_id = '" + Main.userId + "'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("time");
                String type = resultSet.getString("type");
                String money = resultSet.getString("money");
                String category = resultSet.getString("category");
                String description = resultSet.getString("description");
                transactions.add(new Personal(id, date, type, money, category, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        populateTable(transactions);
    }

    private void populateTable(List<Personal> transactions) {
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        colAmount.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        ColCategory.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        tableContents.getItems().addAll(transactions);


    }

    @FXML
    public void onBtnRefreshClick() {
        loadFxml("Add_Money.fxml");

    }

    @FXML
    public void onBtnDeleteClick() {
        Personal selectedUser = (Personal) tableContents.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Remove the selected user from the table
            tableContents.getItems().remove(selectedUser);
            // You can also delete the user from the database here if needed
        }
        try (Connection connection = MySQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM MONEYDATABASE WHERE id = " + selectedUser.getId();
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSignOut() {
       loadFxml("Register.fxml");
        Stage currentStage = (Stage) ablo.getScene().getWindow();
        currentStage.close();
        Main.userId=0;
        Main.EnteredUsername="";
        Main.EnteredPassword="";

    }
}

