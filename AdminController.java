package com.example.hiba;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminController {
    @FXML
    private Button returnButton;
    @FXML
    void onReturnButtonClick(ActionEvent event) {
        // Get the source node and close its stage
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.close();
    }

    public void showCommandeView(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Commande.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Commande");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLivraisonsView(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("livraisons.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Livraisons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
