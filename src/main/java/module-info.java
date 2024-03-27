module com.example.flowmato {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flowmato to javafx.fxml;
    exports com.example.flowmato;
}