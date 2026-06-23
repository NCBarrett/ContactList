package org.example.contactlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactsDao implements Dao{


    @Override
    public List<Contacts> getAllContacts() {
        List<Contacts> contacts = new ArrayList<>();
        String sql = "select * from ContactList";

        try (Connection conn = DatabaseMgr.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacts.add(new Contacts(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    @Override
    public void addContact(Contacts contacts) {

    }

    @Override
    public void updateContact(Contacts contacts) {

    }

    @Override
    public void deleteContact(Contacts contacts) {

    }
}
