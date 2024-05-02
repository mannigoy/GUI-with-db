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

public class UserViewController {
    @FXML
    ImageView logo;

    @FXML
    Button btnAdd;
    @FXML
    Text txtUsername;
    @FXML
    TableView tableContents;

    @FXML
    private TableColumn<Personal,String> colDate;
    @FXML
    private TableColumn<Personal,String> colType;
    @FXML
    private TableColumn<Personal,String> colAmount;
    @FXML
    private TableColumn<Personal,String> ColCategory;
    @FXML
    private TableColumn<Personal,String> colDescription;




    @FXML
    public void initialize(){
        Image image =new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);
        txtUsername.setText(Main.EnteredUsername);

        getFromTable();
    }

    @FXML
    public void onButtonAddClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add_Money.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  Stage currentStage = (Stage) btnAdd.getScene().getWindow();
       // currentStage.close();
    }

    public void getFromTable() {
        List<Personal> transactions = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM MONEYDATABASE where user_id = '" + Main.userId+"'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                String date = resultSet.getString("time");
                String type = resultSet.getString("type");
                String money = resultSet.getString("money");
                String category = resultSet.getString("category");
                String description = resultSet.getString("description");
                transactions.add(new Personal(date, type, money, category, description));
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


}

