package com.djvl.tpredes.client.chatwindow;

import com.djvl.tpredes.messages.User;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

class CellRenderer implements Callback<ListView<User>, ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<>() {

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getName());

                    ImageView statusImageView = new ImageView();
                    URI uri = new File("src/main/resources/com/djvl/tpredes/images/" + user.getStatus().toString().toLowerCase() + ".png").toURI();
                    Image statusImage = null;
                    try {
                        statusImage = new Image(uri.toURL().toString(), 16, 16, true, true);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    statusImageView.setImage(statusImage);

                    hBox.getChildren().addAll(statusImageView, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}