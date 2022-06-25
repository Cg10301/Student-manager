package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Quản lý sinh viên");
            primaryStage.setScene(scene);

            primaryStage.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {
        launch();
    }
}