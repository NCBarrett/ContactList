package org.example.contactlist;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacts {

    private final int id;
    private final SimpleStringProperty sName;
    private final SimpleStringProperty sPhone;
    private final SimpleStringProperty sEmail;
    private final SimpleStringProperty sAddress;

    public Contacts(int id,
                    String name,
                    String phone,
                    String email,
                    String address) {
        this.id = id;
        this.sName = new SimpleStringProperty(name);
        this.sPhone = new SimpleStringProperty(phone);
        this.sEmail = new SimpleStringProperty(email);
        this.sAddress = new SimpleStringProperty(address);
    }

    public int getId() {return id; }

//    public void setId(int id) { this.id = id; }

    public String getName() { return sName.get(); }
    public String getPhone() { return sPhone.get(); }
    public String getEmail() { return sEmail.get(); }
    public String getAddress() { return sAddress.get(); }

    public StringProperty sNameProperty() { return sName; }
    public StringProperty sPhoneProperty() { return sPhone; }
    public StringProperty sEmailProperty() { return sEmail; }
    public StringProperty sAddressProperty() { return sAddress; }

//    public void setName(String name) { sName.set(name); }
//    public void setPhone(String phone) { sPhone.set(phone); }
//    public void setEmail(String email) { sEmail.set(email); }
//    public void setsAddress(String address) { sAddress.set(address); }

    @Override
    public String toString() {

        return getName() + " - " + getPhone() + " - " + getEmail() +
                " - " + getAddress();
    }
}
