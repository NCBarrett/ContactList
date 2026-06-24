package org.example.contactlist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void init() throws IOException, SQLException {
        DatabaseMgr.getConnection();
        DatabaseMgr.initDB();

        FXMLLoader loader = new FXMLLoader();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ContactsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Contact List App");
        stage.setScene(scene);
        stage.show();
    }
}
