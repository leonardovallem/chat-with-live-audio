package com.djvl.tpredes.client.login;

import com.djvl.tpredes.ChatApplication;
import com.djvl.tpredes.client.chatwindow.ChatController;
import com.djvl.tpredes.client.chatwindow.Listener;
import com.djvl.tpredes.client.util.ResizeHelper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private ImageView Defaultview;
    @FXML
    private ImageView Sarahview;
    @FXML
    private ImageView Dominicview;
    @FXML
    public TextField hostnameTextfield;
    @FXML
    private TextField portTextfield;
    @FXML
    private TextField usernameTextfield;
    public static ChatController con;
    @FXML
    private BorderPane borderPane;
    private double xOffset;
    private double yOffset;
    private Scene scene;

    private static LoginController instance;

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    public void loginButtonAction() throws IOException {
        String hostname = hostnameTextfield.getText();
        int port = Integer.parseInt(portTextfield.getText());
        String username = usernameTextfield.getText();

        URI uri = new File("src/main/resources/com/djvl/tpredes/views/ChatView.fxml").toURI();
        FXMLLoader fmxlLoader = new FXMLLoader(uri.toURL());
        Parent window = fmxlLoader.load();
        con = fmxlLoader.getController();
        Listener listener = new Listener(hostname, port, username, con);
        Thread x = new Thread(listener);
        x.start();
        this.scene = new Scene(window);
    }

    public void showScene() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) hostnameTextfield.getScene().getWindow();
            stage.setResizable(true);
            stage.setWidth(1040);
            stage.setHeight(620);

            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            stage.setScene(this.scene);
            stage.setMinWidth(800);
            stage.setMinHeight(300);
            ResizeHelper.addResizeListener(stage);
            stage.centerOnScreen();
            con.setUsernameLabel(usernameTextfield.getText());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setOnMousePressed(event -> {
            xOffset = ChatApplication.getPrimaryStage().getX() - event.getScreenX();
            yOffset = ChatApplication.getPrimaryStage().getY() - event.getScreenY();
            borderPane.setCursor(Cursor.CLOSED_HAND);
        });

        borderPane.setOnMouseDragged(event -> {
            ChatApplication.getPrimaryStage().setX(event.getScreenX() + xOffset);
            ChatApplication.getPrimaryStage().setY(event.getScreenY() + yOffset);

        });

        borderPane.setOnMouseReleased(event -> {
            borderPane.setCursor(Cursor.DEFAULT);
        });
    }

    public void closeSystem() {
        Platform.exit();
        System.exit(0);
    }

    public void minimizeWindow() {
        ChatApplication.getPrimaryStage().setIconified(true);
    }

    public void showErrorDialog(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(message);
            alert.setContentText("Please check for firewall issues and check if the server is running.");
            alert.showAndWait();
        });

    }
}

