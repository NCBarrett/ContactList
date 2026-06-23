package org.example.contactlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactsDao implements Dao{

    @Override
    public List<Contacts> getAllContacts() {
//        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        List<Contacts> contactsList = new ArrayList<>();
        String sql = "SELECT * FROM ContactList";

        try (Connection conn = DatabaseMgr.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contactsList.add(new Contacts(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
            System.out.println("Inside ContactsDao, Contacts list loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactsList;
    }

    public void addContact(String name, String phone, String email, String address) {
        String sql = """
            insert into ContactList(name, phone, email, address)
            values (?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseMgr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(2, name);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            System.out.println("Contact added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateContact(Contacts contacts) {

    }

    public void deleteContact(Contacts contacts) {

    }
}
