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

    public void addContact(String sName, String sPhone, String sEmail, String sAddress) {
        String sql = """
            insert into ContactList(name, phone, email, address)
            values (?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseMgr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sName);
            pstmt.setString(2, sPhone);
            pstmt.setString(3, sEmail);
            pstmt.setString(4, sAddress);

            pstmt.executeUpdate();
            System.out.println("Contact added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateContact(String sName, String sPhone, String sEmail, String sAddress,
                              int id) {
        // Check to see if 1) *any* item is clicked and 2) the item is blank

        String name = "";
        String phone = "";
        String email = "";
        String address = "";
        StringBuilder sql = new StringBuilder("UPDATE ContactList SET ");
        List<Object> params = new ArrayList<>();

        if (sName != null) {
            sql.append("name = ?, ");
            name = sName;
            params.add(name);
        }

        if (sPhone != null) {
            sql.append("phone = ?, ");
            phone = sPhone;
            params.add(phone);
        }

        if (sEmail != null) {
            sql.append("email = ?, ");
            email = sEmail;
            params.add(email);
        }

        if (sAddress != null) {
            sql.append("address = ?, ");
            address = sAddress;
            params.add(address);
        }

        if (params.isEmpty()) {
            return;
        }
        sql.setLength(sql.length() - 2);

        System.out.println("SQL: " + sql.toString());

        try (Connection conn = DatabaseMgr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                System.out.println((i + 1) + ": " + params.get(i));
                pstmt.setObject(i + 1, params.get(i));
            }

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