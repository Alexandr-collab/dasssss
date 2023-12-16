package com.example.das;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 840);
        HelloController controller = fxmlLoader.getController();
        controller.getStage(stage);
        stage.setTitle("Лабораторная 1.8");
        stage.setScene(scene);
//        stage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("/com/example/labs38/images/logo.png"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}