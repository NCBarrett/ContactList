

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
    List<Contacts> people = contactsDao.getAllContacts();
    private final ObservableList<Contacts> contactsList =
            FXCollections.observableArrayList(people);

    public Controller() {
        this.contactsDao = new ContactsDao();
    }

    @FXML
    private void initialize() {
        addDetails.setVisible(false);
        addDetails.setManaged(false);
//        System.out.println("Initializing page");

        TableColumn<Contacts, String> nameCol = new TableColumn<>("Name");
        TableColumn<Contacts, String> phoneCol = new TableColumn<>("Phone");
        TableColumn<Contacts, String> addressCol = new TableColumn<>("Address");
        TableColumn<Contacts, String> emailCol = new TableColumn<>("Email");

        contactsTableView.getColumns().add(nameCol);
        contactsTableView.getColumns().add(phoneCol);
        contactsTableView.getColumns().add(addressCol);
        contactsTableView.getColumns().add(emailCol);

        nameCol.setCellValueFactory(cellData ->
                cellData.getValue().sNameProperty());
        phoneCol.setCellValueFactory(cellData ->
                cellData.getValue().sPhoneProperty());
        emailCol.setCellValueFactory(cellData ->
                cellData.getValue().sEmailProperty());
        addressCol.setCellValueFactory(cellData ->
                cellData.getValue().sAddressProperty());
        contactsTableView.setEditable(false);
        contactsTableView.setItems(contactsList);
    }

    @FXML
    private void handleAddButton() {
        toggleHBox();
    }

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        contactsDao.addContact(name, phone, email, address);
        clearAll();
    }

    @FXML
    private void handleChangeBtn() {
        // need to read what's changed and then send only those entries to the UPDATE
        // query

        Contacts person = this.contactsTableView.getSelectionModel().getSelectedItem();
        if (person == null) {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection alert");
            alert.setContentText("Please select a contact");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            String name = "";
            String phone = "";
            String email = "";
            String address = "";
            toggleHBox();

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
            clearAll();

            toggleHBox();
        }
    }

    @FXML
    private void handleDelBtn() {
        Contacts person = this.contactsTableView.getSelectionModel().getSelectedItem();
        if (person == null) {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection alert");
            alert.setContentText("Please select a contact");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection alert");
            alert.setContentText("Are you sure you want to delete this contact?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int id = contactsTableView.getSelectionModel().getSelectedItem().getId();
                contactsDao.deleteContact(id);
            }
        }
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

    public void toggleHBox() {
        boolean isVisible = addDetails.isVisible();
        boolean isManaged = addDetails.isManaged();
        addDetails.setVisible(!isVisible);
        addDetails.setManaged(!isManaged);
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

//    private void loadContacts() {
//        System.out.println("Loading contacts");
//        contactsList.clear();
//        List<Contacts> people = contactsDao.getAllContacts();
//        if (people.isEmpty()) {
//            System.out.println();
//        }
////        var rawList = contactsDao.getAllContacts();
//
//        ObservableList<Contacts> observableList =
//                FXCollections.observableArrayList(people);
//
//        contactsTableView.setItems(observableList);
//    }