package com.example.csit228f2_2.Final;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Personal {
    private int id;
    private final SimpleStringProperty date;
    private final SimpleStringProperty type;
    private final SimpleStringProperty amount;
    private final SimpleStringProperty category;
    private final SimpleStringProperty description;

    public int getId() {
        return id;
    }

    public Personal(int id, String date, String type, String amount, String category, String description) {
        this.id=id;
        this.date = new SimpleStringProperty(date);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleStringProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.description = new SimpleStringProperty(description);
    }

    // Add getters for all properties
    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}





