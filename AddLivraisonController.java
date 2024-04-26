package com.example.hiba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddLivraisonController {

    @FXML
    private TextField commandeField;

    @FXML
    private TextField adresseField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> confirmerComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField prixField;

    @FXML
    private Label dateLabel;

    @FXML
    private Label prixLabel;

    private LivraisonService livraisonService = new LivraisonService();

    @FXML
    void initialize() {
        // Set the text of the dateLabel to the current date
        dateLabel.setText(LocalDate.now().toString());

    }

    @FXML
    void onAddButtonClick(ActionEvent actionEvent) {
        // Retrieve values from UI components
        String commande = commandeField.getText().trim();
        String adresse = adresseField.getText().trim();
        LocalDate date = datePicker.getValue();
        String confirmer = confirmerComboBox.getValue();
        String email = emailField.getText().trim();
        String prixText = prixField.getText().trim();

        // Validate input
        if (commande.isEmpty() || adresse.isEmpty() || date == null || confirmer == null || email.isEmpty() || prixText.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            showAlert("Please enter a valid email address.");
            return;
        }

        // Validate prix format
        double prix;
        try {
            prix = Double.parseDouble(prixText);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid price.");
            return;
        }

        // Create a Livraison object
        Livraison livraison = new Livraison(commande, adresse, date, Boolean.parseBoolean(confirmer), email, prix);

        // Add the Livraison object to the database
        livraisonService.add(livraison);

        // Close the window or show a confirmation message
        // You can add your logic here based on your application's requirements
    }

    @FXML
    void onAddButtonFrontClick(ActionEvent actionEvent) {
        // Retrieve values from UI components
        String adresse = adresseField.getText().trim();
        LocalDate date = LocalDate.parse(dateLabel.getText());
        String email = emailField.getText().trim();
        double prix = 5; // Assuming the price is always 5

        // Validate input
        if (adresse.isEmpty() || email.isEmpty()) {
            showAlert("Address and email fields are required.");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            showAlert("Please enter a valid email address.");
            return;
        }

        // Create a Livraison object
        Livraison livraison = new Livraison(adresse, date, email, prix);

        // Add the Livraison object to the database
        livraisonService.add(livraison);

        // Close the window or show a confirmation message
        // You can add your logic here based on your application's requirements
    }

    private boolean isValidEmail(String email) {
        // Simple email validation regex
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
