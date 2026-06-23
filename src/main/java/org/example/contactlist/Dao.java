package org.example.contactlist;

import java.util.List;

public interface Dao {

    List<Contacts> getAllContacts();
    void addContact(Contacts contacts);
    void updateContact(Contacts contacts);
    void deleteContact(Contacts contacts);
}
