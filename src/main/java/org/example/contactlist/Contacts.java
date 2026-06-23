package org.example.contactlist;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacts {

    private int id;
    private StringProperty name;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty address;

    public Contacts(int id,
                    String name,
                    String phone,
                    String email,
                    String address) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
    }

    public int getId() {return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public void setName(StringProperty name) { this.name = name; }

    public String getPhone() { return phone.get(); }

    public void setPhone(StringProperty phone) { this.phone = phone; }

    public String getEmail() { return email.get(); }

    public void setEmail(StringProperty email) { this.email = email; }

    public String getAddress() { return address.get(); }

    public void setAddress(StringProperty address) { this.address = address; }
}
