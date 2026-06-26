

package org.example.contactlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public TableView<Contacts> contactsTableView = new TableView<>();

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

    private ContactsDao contactsDao = new ContactsDao();
    private DatabaseMgr dbMgr;
    private ObservableList<Contacts> contactsList =
            FXCollections.observableArrayList();

    public Controller() {
        this.contactsDao = new ContactsDao();
    }

    @FXML
    private void initialize() {
        addDetails.setVisible(false);
        addDetails.setManaged(false);
//        System.out.println("Initializing page");
        loadContacts();
    }

    @FXML
    private void handleToggle() {
        boolean isVisible = addDetails.isVisible();
        boolean isManaged = addDetails.isManaged();
        addDetails.setVisible(!isVisible);
        addDetails.setManaged(!isManaged);
    }

    private void loadContacts() {
        System.out.println("Loading contacts");
        contactsList.clear();
        List<Contacts> people = contactsDao.getAllContacts();
//        var rawList = contactsDao.getAllContacts();
        if (people.isEmpty()) {
            System.out.println();
        }
        ObservableList<Contacts> observableList =
                FXCollections.observableArrayList(people);

        contactsTableView.setEditable(false);

        TableColumn<Contacts, String> nameCol = new TableColumn<>("Name");
        TableColumn<Contacts, String> phoneCol = new TableColumn<>("Phone");
        TableColumn<Contacts, String> addressCol = new TableColumn<>("Address");
        TableColumn<Contacts, String> emailCol = new TableColumn<>("Email");

        contactsTableView.getColumns().addAll(nameCol, phoneCol, emailCol, addressCol);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        contactsTableView.setItems(observableList);
    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        contactsDao.addContact(name, phone, email, address);
        loadContacts();
        clearAll();
    }

    @FXML
    private void handleChangeBtn() {
        // need to read what's changed and then send only those entries to the UPDATE
        // query

        String name = "";
        String phone = "";
        String email = "";
        String address = "";
        Contacts person = this.contactsTableView.getSelectionModel().getSelectedItem();
        if (person == null) {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection alert");
            alert.setContentText("Please select a contact");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            int id = person.getId();

            if (!nameField.getText().isEmpty()) {
                name = nameField.getText();
            }

            if (!phoneField.getText().isEmpty()) {
                phone = phoneField.getText();
            }
            if (!emailField.getText().isEmpty()) {
                email = emailField.getText();
            }

            if (!addressField.getText().isEmpty()) {
                address = addressField.getText();
            }

            contactsDao.updateContact(name, phone, email, address, id);
            loadContacts();
            clearAll();
        }
    }

    @FXML
    private void handleDelBtn() {
        int id = contactsTableView.getSelectionModel().getSelectedItem().getId();
        contactsDao.deleteContact(id);
        loadContacts();
    }

    public ObservableList<Contacts> getContactsList() {
        return contactsList;
    }

    private void clearAll() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
    }
}

//public void setContactsList(ObservableList<Contacts> contactsList) {
//    this.contactsList = contactsList;
//}

//        contactsList = FXCollections.observableArrayList();

//        contactListView.getItems().addAll(contactsDao.getAllContacts());
//        contactListView.setCellFactory(lv -> new ListCell<Contacts>() {
//            @Override
//            protected void updateItem(Contacts item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty ? "" : item.getName());
//                setText(empty ? "" : item.getPhone());
//                setText(empty ? "" : item.getsEmail());
//                setText(empty ? "" : item.getsAddress());
//            }
//        });

//        ObservableList<String> observableContacts =
//                FXCollections.observableArrayList(
//                        contactsDao.getAllContacts().toString());
//        this.contactListView.setItems(observableContacts.toString());