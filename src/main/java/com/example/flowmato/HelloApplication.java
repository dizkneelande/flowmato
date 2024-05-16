package com.example.flowmato;

import com.example.flowmato.controller.AudioController;
import com.example.flowmato.controller.NotificationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.flowmato.model.SqliteProfileDAO;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    public static final String TITLE = "Flowmato";

    public static NotificationController notificationController;
    public static AudioController audioController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 360);
        stage.setTitle(TITLE);

        // Set the icon to Flowmato.png
        stage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("/images/Flowmato.png"))));

        stage.setScene(scene);
        stage.show();

        stage.setScene(scene);
        stage.show();
        SqliteProfileDAO dao = new SqliteProfileDAO();
        dao.initialiseDatabase();
        audioController = new AudioController();
    }

    public static void main(String[] args) {
        launch();
    }
}