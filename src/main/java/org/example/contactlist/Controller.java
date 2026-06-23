

package org.example.contactlist;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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
    public ListView<String> contactList;

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

    @FXML
    private void initialize() {
        addDetails.setVisible(false);
        addDetails.setManaged(false);
    }
}