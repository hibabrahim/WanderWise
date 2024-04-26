package com.example.hiba;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import java.io.IOException;

public class CommandeController {

    @FXML
    private Button returnButton;

    @FXML
    private Button livraisonsButton;
    @FXML
    private TableView<Commande> commandeTableView;

    @FXML
    private TableColumn<Commande, String> dateColumn;

    @FXML
    private TableColumn<Commande, String> statueColumn;

    @FXML
    private TableColumn<Commande, Double> remiseColumn;

    @FXML
    private TableColumn<Commande, Integer> userIdColumn;

    @FXML
    private TableColumn<Commande, Void> deleteColumn; // New column for delete buttons

    @FXML
    private TextField searchField;

    private CommandeService commandeService;
    @FXML
    void initialize() {
        // Initialize the CommandeService
        commandeService = new CommandeService();

        // Bind columns to properties of the Commande class
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        statueColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        remiseColumn.setCellValueFactory(new PropertyValueFactory<>("remise"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        // Make columns editable
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(event -> {
            Commande commande = event.getRowValue();
            commande.setDate(LocalDate.parse(event.getNewValue()));
            commandeService.update(commande);
        });

        statueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statueColumn.setOnEditCommit(event -> {
            Commande commande = event.getRowValue();
            commande.setStatue(event.getNewValue());
            commandeService.update(commande);
        });

        remiseColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        remiseColumn.setOnEditCommit(event -> {
            Commande commande = event.getRowValue();
            commande.setRemise(event.getNewValue());
            commandeService.update(commande);
        });

        userIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        userIdColumn.setOnEditCommit(event -> {
            Commande commande = event.getRowValue();
            commande.setUser_id(event.getNewValue());
            commandeService.update(commande);
        });
        commandeTableView.setEditable(true);

        // Add delete buttons to each row
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Commande commande = getTableView().getItems().get(getIndex());
                        commandeService.supp(commande); // Implement this method in your CommandeService
                        loadData(); // Reload data after deletion
                    });
                }
            }
        });

        // Load data into the table
        loadData();
    }

    @FXML
    void onSearchButtonClick() {
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            List<Commande> searchResults = commandeService.search(searchText);
            ObservableList<Commande> observableSearchResults = FXCollections.observableArrayList(searchResults);
            commandeTableView.setItems(observableSearchResults);
        } else {
            loadData(); // Reload all data if search field is empty
        }
    }

    private void loadData() {
        // Retrieve data from the CommandeService
        List<Commande> commandeList = commandeService.show();

        // Convert the list to an ObservableList
        ObservableList<Commande> observableCommandeList = FXCollections.observableArrayList(commandeList);

        // Set the items in the TableView
        commandeTableView.setItems(observableCommandeList);
    }
    @FXML
    void onAddButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCommande.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Wait for the add commande window to close
            stage.setOnCloseRequest(event -> loadData());

            stage.showAndWait(); // Show the window and wait for it to close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onReturnButtonClick(ActionEvent event) {
        // Get the source node and close its stage
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onLivraisonsButtonClick(ActionEvent event) {
        // Load livraisons.fxml and show it in a new stage
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
    @FXML
    void onEditCommit(TableColumn.CellEditEvent<Commande, ?> event) {
        Commande editedCommande = event.getRowValue();
        Object newValue = event.getNewValue();

        if (editedCommande != null) {
            // Update the corresponding property in the Commande object
            if (event.getSource() == dateColumn) {
                editedCommande.setDate(LocalDate.parse(newValue.toString()));
            } else if (event.getSource() == statueColumn) {
                editedCommande.setStatue(newValue.toString());
            } else if (event.getSource() == remiseColumn) {
                editedCommande.setRemise(Double.parseDouble(newValue.toString()));
            } else if (event.getSource() == userIdColumn) {
                editedCommande.setUser_id(Integer.parseInt(newValue.toString()));
            }

            // Update the underlying data
            commandeService.update(editedCommande);
        }
    }


}
