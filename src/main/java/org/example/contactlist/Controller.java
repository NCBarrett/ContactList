

package org.example.contactlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML public VBox root;
    @FXML public TableView<Contacts> contactsTableView = new TableView<>();
    @FXML public HBox controlBtns;
    @FXML public HBox addDetails;

    @FXML public Label name;
    @FXML public Label phone;
    @FXML public Label email;
    @FXML public Label address;

    @FXML public TextField emailField;
    @FXML public TextField nameField;
    @FXML public TextField phoneField;
    @FXML public TextField addressField;

    @FXML public Button submitNew;
    @FXML public Button deleteBtn;
    @FXML public Button submitChg;

    @FXML public ToggleButton addToggle;
    @FXML public ToggleButton chgToggle;

    @FXML TableColumn<Contacts, String> nameCol = new TableColumn<>("Name");
    @FXML TableColumn<Contacts, String> phoneCol = new TableColumn<>("Phone");
    @FXML TableColumn<Contacts, String> addressCol = new TableColumn<>("Address");
    @FXML TableColumn<Contacts, String> emailCol = new TableColumn<>("Email");

    private ContactsDao contactsDao = new ContactsDao();
    List<Contacts> people = contactsDao.getAllContacts();
    private final ObservableList<Contacts> contactsList =
            FXCollections.observableArrayList(people);

    public Controller() {
        this.contactsDao = new ContactsDao();
        Stage stage;
//        stage = (Stage) root.getScene().getWindow();
//        stage = (Stage) addDetails.getScene().getWindow();
    }

    @FXML private void initialize() {
        addDetails.setVisible(false);
        addDetails.setManaged(false);

        contactsTableView.getColumns().add(nameCol);
        contactsTableView.getColumns().add(phoneCol);
        contactsTableView.getColumns().add(emailCol);
        contactsTableView.getColumns().add(addressCol);

        nameCol.setCellValueFactory(cellData ->
                cellData.getValue().sNameProperty());
        phoneCol.setCellValueFactory(cellData ->
                cellData.getValue().sPhoneProperty());
        emailCol.setCellValueFactory(cellData ->
                cellData.getValue().sEmailProperty());
        addressCol.setCellValueFactory(cellData ->
                cellData.getValue().sAddressProperty());

        addressCol.setMinWidth(400);
        contactsTableView.setPrefHeight(200.0); ;

        contactsTableView.setEditable(false);
        contactsTableView.setItems(contactsList);

        submitNew.setVisible(false);
        submitChg.setVisible(false);

        contactsTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable,
                              oldValue, newValue) -> {

                    System.out.println("submitNew.isVisible() = " +
                            submitNew.isVisible() + "; submitChg.isVisible() = " +
                            submitChg.isVisible());
                    if (submitChg.isVisible()) {
                        if (newValue != null) {
                            nameField.setText(newValue.sNameProperty().get());
                            phoneField.setText(newValue.sPhoneProperty().get());
                            addressField.setText(newValue.sAddressProperty().get());
                            emailField.setText(newValue.sEmailProperty().get());
                        }
                    }
                });
    }

    @FXML private void addToggle(Event event) {
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();

//        System.out.println("submitNew.isVisible() = " +
//                submitNew.isVisible() + "; submitChg.isVisible() = " +
//                submitChg.isVisible());
        if (addDetails.isVisible()) {

            clearAll();
            if (submitNew.isVisible()) {
                addDetails.setVisible(false);
                addDetails.setManaged(false);
            } else {
                submitNew.setVisible(true);
                submitChg.setVisible(false);
            }
        } else {
            addDetails.setVisible(true);
            addDetails.setManaged(true);
            submitChg.setVisible(false);
            submitNew.setVisible(true);
        }
        stage.sizeToScene();
    }

    @FXML private void chgToggle(Event event) {
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();

        System.out.println("submitNew.isVisible() = " +
                submitNew.isVisible() + "; submitChg.isVisible() = " +
                submitChg.isVisible());

        if (addDetails.isVisible()) {
            if (submitChg.isVisible()) {
                clearAll();
                addDetails.setVisible(false);
                addDetails.setManaged(false);
            } else {
                submitChg.setVisible(true);
                submitNew.setVisible(false);
            }
        } else {
            addDetails.setVisible(true);
            addDetails.setManaged(true);
            submitChg.setVisible(true);
            submitNew.setVisible(false);
//        toggleHBox();
        }

        stage.sizeToScene();

        if (contactsTableView.getSelectionModel().getSelectedItem() != null) {
            nameField.setText(contactsTableView.getSelectionModel().getSelectedItem()
                    .sNameProperty().get());
            phoneField.setText(contactsTableView.getSelectionModel().getSelectedItem()
                    .sPhoneProperty().get());
            emailField.setText(contactsTableView.getSelectionModel().getSelectedItem()
                    .sEmailProperty().get());
            addressField.setText(contactsTableView.getSelectionModel().getSelectedItem()
                    .sAddressProperty().get());
        }
    }

    @FXML private void submitNew() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        contactsDao.addContact(name, phone, email, address);
        clearAll();
        List<Contacts> people = contactsDao.getAllContacts(); // does this cause a memory leak?
        ObservableList<Contacts> contactsList =
                FXCollections.observableArrayList(people);
        contactsTableView.setItems(contactsList);
        contactsTableView.getSelectionModel().clearSelection();
    }

    @FXML private void submitChg() {
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

//            System.out.println("id = " + id + "; name = " + name + "; phone = " + phone +
//                    "; email = " + email + "; address = " + address);
            contactsDao.updateContact(name, phone, email, address, id);
            clearAll();
            List<Contacts> people = contactsDao.getAllContacts(); // does this cause a memory leak?
            ObservableList<Contacts> contactsList =
                    FXCollections.observableArrayList(people);
            contactsTableView.setItems(contactsList);
            contactsTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML private void handleDelBtn() {
        Contacts person = this.contactsTableView.getSelectionModel().getSelectedItem();
        if (person == null) {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection alert");
            alert.setContentText("Please select a contact");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new  Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you want to delete this contact?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int id = contactsTableView.getSelectionModel().getSelectedItem().getId();
                contactsDao.deleteContact(id);
            }
        }
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
        submitChg.setVisible(false);
        submitNew.setVisible(false);
    }
}
