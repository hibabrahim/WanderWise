package com.example.hiba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {
    @FXML
    private Button returnButton;
    @FXML
    void onReturnButtonClick(ActionEvent event) {
        // Get the source node and close its stage
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void checkout(ActionEvent event) {
        try {
            // Load the Livraisons.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutLivraison.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Livraisons Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
