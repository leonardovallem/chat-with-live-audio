package com.djvl.tpredes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ChatBubble extends VBox {
    private String message;
    private Query query;

    public ChatBubble() {
        this("mensagem", Query.SENT);
    }

    public ChatBubble(String message, Query query) {
        super(new Text(message));
        var text = ((Text) super.getManagedChildren().get(0));

        super.setAlignment(Pos.CENTER_RIGHT);
        super.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.rgb(0, 255, 255),
                                new CornerRadii(16.0),
                                null
                        )
                )
        );

        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.rgb(0, 0, 0));

        setAlignment(Pos.CENTER);
        this.message = message;
        this.query = query;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
