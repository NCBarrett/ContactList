package org.example.contactlist;

import eu.hansolo.toolbox.observables.ObservableList;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static javax.management.remote.JMXConnectorFactory.connect;

public class DatabaseMgr {

    // Connection string pointing to local file
    private static final String URL =
            "jdbc:sqlite:C://Users//barre//Documents//ContactList//CcntactList.db";

    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {;

//        System.out.println("Connecting to database...");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Create a default table if it doesn't exist
    public static void initDB() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS main.ContactList (
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     name TEXT NOT NULL,
                     sPhone TEXT NOT NULL,
                     sEmail TEXT NOT NULL,
                     sAddress TEXT NOT NULL
                     )""";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
//            System.out.println("Database and table successfully verified/created.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
