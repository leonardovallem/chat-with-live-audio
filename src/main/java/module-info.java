module com.djvl.tpredes {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    opens com.djvl.tpredes to javafx.fxml, javafx.graphics;
    opens com.djvl.tpredes.client.login to javafx.fxml, javafx.graphics;
    opens com.djvl.tpredes.client.chatwindow to javafx.fxml, javafx.graphics;
    exports com.djvl.tpredes to javafx.fxml;
    exports com.djvl.tpredes.client.login to javafx.fxml;
    exports com.djvl.tpredes.client.chatwindow to javafx.fxml;
}