package org.example.contactlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactsDao implements Dao{

    @Override
    public ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
//        List<Contacts> contactsList = new ArrayList<>();
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
//            System.out.println("Inside ContactsDao, Contacts list loaded successfully");
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

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);

            pstmt.executeUpdate();
//            System.out.println("Contact added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateContact(int id, String name, String phone, String email, String address) {
        String sql = """
            UPDATE ContactList SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?
            """;

        try (Connection conn = DatabaseMgr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();
            System.out.println("Contact Updated successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteContact(Contacts contacts) {

    }
}
