module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires javafx.graphics;
    requires java.base;

    opens com.example.flowmato to javafx.fxml;
    exports com.example.flowmato;
    exports com.example.flowmato.controller;
    opens com.example.flowmato.controller to javafx.fxml;
    exports com.example.flowmato.model;
    opens com.example.flowmato.model to javafx.fxml;
}