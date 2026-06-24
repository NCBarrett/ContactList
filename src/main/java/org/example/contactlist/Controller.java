

package org.example.contactlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;

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
    //private ListView<Contacts> contactListView;
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
//        System.out.println("Initializing page");
        loadContacts();
    }
    private void loadContacts() {
        System.out.println("Loading contacts");
//        List<Contacts> contacts = contactsDao.getAllContacts();
        ObservableList<String> observableContacts =
                FXCollections.observableArrayList(
                        contactsDao.getAllContacts().toString());
        this.contactListView.setItems(observableContacts);

    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        contactsDao.addContact(name, phone, email, address);
        loadContacts();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
    }

    @FXML
    private void handleChangeBtn() {
        String person = this.contactListView.getSelectionModel().getSelectedItem();

        if (person != null) {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection alert");
            alert.setContentText("Please select a contact");
            Optional<ButtonType> result = alert.showAndWait();
        }

        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        contactsDao.updateContact(name, phone, email, address);
        loadContacts();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
    }

    // Add a function to clear all input fields
}