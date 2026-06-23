

package org.example.contactlist;

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
    public ListView ContactList;

    @FXML
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
    private void initialize() {
        addDetails.setVisible(false);
        addDetails.setManaged(false);
    }

    @FXML
    private void handleToggle() {
        boolean isVisible = addDetails.isVisible();
        boolean isManaged = addDetails.isManaged();
        addDetails.setVisible(!isVisible);
        addDetails.setManaged(!isManaged);
    }

    private ContactsDao contactsDao = new ContactsDao();

    private void loadContacts() {
        List<Contacts> contacts = contactsDao.getAllContacts();
        contactListView.getItems().clear();
        for (Contacts contact : contacts) {
            contactListView.getItems().add(contact.getName() + " - " +
                    contact.getPhone() + " - " + contact.getEmail() +
                    " - " + contact.getAddress());
        }
    }
}