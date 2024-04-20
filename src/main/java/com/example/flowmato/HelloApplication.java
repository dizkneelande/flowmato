package com.example.flowmato;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.flowmato.model.SqliteProfileDAO;

import java.io.IOException;

public class HelloApplication extends Application {
    //Constants defining the window title and size
    public static final String TITLE = "Flowmato";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
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