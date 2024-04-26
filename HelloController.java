package com.example.hiba;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button adminButton; // Button for admin

    @FXML
    private Button userButton; // Button for user



    @FXML
    protected void onAdminButtonClick() {
        // Load the admin.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Panel");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onUserButtonClick() {
        // Load the user.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("market.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User Panel");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
