package com.djvl.tpredes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class ChatApplication extends Application {
    private static Stage primaryStageObj;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj = primaryStage;
        URI uri = new File("src/main/resources/com/djvl/tpredes/views/LoginView.fxml").toURI();
        Parent root = FXMLLoader.load(uri.toURL());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Socket Chat : Client version 0.3");

        uri = new File("src/main/resources/com/djvl/tpredes/images/plug.png").toURI();
        primaryStage.getIcons().add(new Image(uri.toURL().toString()));

        Scene mainScene = new Scene(root, 350, 420);
        mainScene.setRoot(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }
}