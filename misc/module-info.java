open module test.module {
    requires com.example.addressbook;
    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires org.assertj.core;
    requires org.testfx;
    requires org.testfx.junit5;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
}