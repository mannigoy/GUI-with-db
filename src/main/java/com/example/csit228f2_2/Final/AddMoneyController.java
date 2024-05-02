package com.example.csit228f2_2.Final;

import com.example.csit228f2_2.MySQLConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddMoneyController implements Initializable {
@FXML
DatePicker datePicker;
@FXML
RadioButton radIncome,radExpense;
@FXML
TextField txtAmount;
@FXML
ChoiceBox <String> choiceCategory;
@FXML
TextArea txtAreaDescription;

@FXML
Button btnAddMount;
String st[] = { "Food", "Social Life", "Pets", "Transport","Culture","Household", "Apparel", "Beauty", "Health", "Education", "Gift", "Other", "Load" };
ToggleGroup toggleGroup = new ToggleGroup();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceCategory.getItems().addAll(st);
        radIncome.setToggleGroup(toggleGroup);
        radExpense.setToggleGroup(toggleGroup);
    }


    void InsertData(){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO moneydatabase (user_id,time, type, money, category, description) VALUES (?, ?,?,?,?,?)")){

            LocalDate selectedDate = datePicker.getValue();
            String type = radIncome.isSelected() ? "Income" : "Expense";
            double amount = Double.parseDouble(txtAmount.getText());
            String category = choiceCategory.getValue();
            String description = txtAreaDescription.getText();
            statement.setInt(1,Main.userId);
            statement.setObject(2, selectedDate);
            statement.setString(3, type);
            statement.setDouble(4, amount);
            statement.setString(5, category);
            statement.setString(6, description);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void OnclickBtnAdd(){
        InsertData();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnAddMount.getScene().getWindow();
            stage.setScene(scene);
           stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
