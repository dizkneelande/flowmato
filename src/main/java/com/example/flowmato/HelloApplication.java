package com.example.flowmato;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.flowmato.model.SqliteProfileDAO;

import java.io.IOException;

public class HelloApplication extends Application {
    //constants defining the window title and size
    public static final String TITLE = "Flowmato";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 360);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
        SqliteProfileDAO dao = new SqliteProfileDAO();
        dao.initialiseDatabase();
    }

    public static void main(String[] args) {
        launch();
    }
}