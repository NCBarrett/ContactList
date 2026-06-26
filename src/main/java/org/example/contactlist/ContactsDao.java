package org.example.contactlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactsDao implements Dao{

    @Override
    public List<Contacts> getAllContacts() {

        List<Contacts> contactsList = new ArrayList<>();
        String sql = "SELECT * FROM ContactList";

        try (Connection conn = DatabaseMgr.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contactsList.add(new Contacts(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("sPhone"),
                        rs.getString("sEmail"),
                        rs.getString("sAddress")
                ));
            }
//            System.out.println("Inside ContactsDao, Contacts list loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactsList;
    }

    public void addContact(String name, String sPhone, String sEmail, String sAddress) {
        String sql = """
            insert into ContactList(name, sPhone, sEmail, sAddress)
            values (?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseMgr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, sPhone);
            pstmt.setString(3, sEmail);
            pstmt.setString(4, sAddress);

            pstmt.executeUpdate();
            System.out.println("Contact added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateContact(String name, String phone, String email, String address, int id) {
        String sql = """
            UPDATE ContactList SET name = ?, sPhone = ?, sEmail = ?, sAddress = ? WHERE id = ?
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

    public void deleteContact(int id) {
        String  sql = """
            delete from ContactList where id = ?;
        """;

        try (Connection conn = DatabaseMgr.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Contact deleted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
}
//   public ObservableList<Contacts> getAllContacts() {
//        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();