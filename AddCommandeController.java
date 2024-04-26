package com.example.hiba;

import com.example.hiba.Commande;
import com.example.hiba.CommandeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class AddCommandeController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private TextField remiseField;

    @FXML
    private TextField userIdField;

    public void onAddButtonClick(ActionEvent actionEvent) {
        // Retrieve values from UI components
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
        String statut = statutComboBox.getValue();
        String remiseText = remiseField.getText().trim();
        String userIdText = userIdField.getText().trim();
      System.out.println(statut);
        // Validate input
        if (date.isEmpty() || statut == null || remiseText.isEmpty() || userIdText.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        double remise;
        int userId;

        // Validate remise
        try {
            remise = Double.parseDouble(remiseText);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid remise.");
            return;
        }

        // Validate userId
        try {
            userId = Integer.parseInt(userIdText);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid user ID.");
            return;
        }

        // Create Commande object and add it using CommandeService
        Commande commande = new Commande(date, statut, remise, userId);
        CommandeService commandeService = new CommandeService();
        commandeService.add(commande);

        // Clear fields after adding
        clearFields();
    }
    @FXML
    void initialize() {
        // Populate the statutComboBox
        statutComboBox.getItems().addAll("En cours de preparation", "En cours", "arrivee");

        // Set the default value for the statutComboBox, if needed
        statutComboBox.setValue("En cours de preparation");

        // Set the text of the datePicker to the current date
    }

    private void clearFields() {
        datePicker.setValue(null);
        statutComboBox.getSelectionModel().clearSelection();
        remiseField.clear();
        userIdField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
