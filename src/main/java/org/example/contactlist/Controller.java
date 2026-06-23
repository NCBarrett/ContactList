

package org.example.contactlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class Controller {

    @FXML
    public HBox addDetails;

    @FXML
    public Label name;

    @FXML
    public Label phone;

    @FXML
    public Label email;

    @FXML
    public Label address;

    @FXML
    public ListView contactList;

    @FXML
//    private ListView<Contacts> contactListView;
    public ListView<String> contactListView;

    @FXML
    public TextField emailField;

    @FXML
    public TextField nameField;

    @FXML
    public TextField phoneField;

    @FXML
    public TextField addressField;

    @FXML
    public Button submit;

    @FXML
    public Button changeBtn;

    @FXML
    public Button deleteBtn;

    @FXML
    public ToggleButton addToggle;

    @FXML
    private void handleToggle() {
        boolean isVisible = addDetails.isVisible();
        boolean isManaged = addDetails.isManaged();
        addDetails.setVisible(!isVisible);
        addDetails.setManaged(!isManaged);
    }

    private final ContactsDao contactsDao = new ContactsDao();
    private DatabaseMgr dbMgr;
    private ObservableList<Contacts> contacts =
            FXCollections.observableArrayList(contactsDao.getAllContacts());

    @FXML
    private void initialize() {
        addDetails.setVisible(false);
        addDetails.setManaged(false);
        System.out.println("Initializing page");
        loadContacts();
    }
    private void loadContacts() {
        System.out.println("Loading contacts");
//        List<Contacts> contacts = contactsDao.getAllContacts();
        ObservableList<String> observableContacts =
                FXCollections.observableArrayList(
                        contactsDao.getAllContacts().toString());

//        contactListView.setItems(observableContacts);
//        for (Contacts contact : contacts) {
//            this.contactListView.getItems().add(contact.getName() + " - " +
//                    contact.getPhone() + " - " + contact.getAddress() +
//                    " - " + contact.getEmail());
//        }
    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        contactsDao.addContact(name, phone, email, address);
    }

}