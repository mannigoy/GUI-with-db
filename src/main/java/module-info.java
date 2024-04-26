module com.example.csit228f2_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.csit228f2_2 to javafx.fxml;
    exports com.example.csit228f2_2;
    exports com.example.csit228f2_2.Final;
    opens com.example.csit228f2_2.Final to javafx.fxml;
}