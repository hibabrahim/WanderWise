package com.example.hiba;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LivraisonController {

    @FXML
    private Button returnButton;

    @FXML
    private Button commandesButton;

    @FXML
    private TableView<Livraison> livraisonTableView;

    @FXML
    private TableColumn<Livraison, String> commandeColumn;

    @FXML
    private TableColumn<Livraison, String> adresseColumn;

    @FXML
    private TableColumn<Livraison, LocalDate> dateColumn;

    @FXML
    private TableColumn<Livraison, Void> confirmerColumn;

    @FXML
    private TableColumn<Livraison, String> emailColumn;

    @FXML
    private TableColumn<Livraison, Double> prixColumn;

    @FXML
    private TableColumn<Livraison, Void> deleteColumn; // New column for delete buttons

    @FXML
    private TextField searchField;
    @FXML
    private Label dateLabel;
    private LivraisonService livraisonService;

    @FXML
    void initialize() {
        // Initialize the LivraisonService
        livraisonService = new LivraisonService();

        // Bind columns to properties of the Livraison class
        commandeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCommande()));
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        confirmerColumn.setCellFactory(param -> new TableCell<>() {
            private final Button confirmerButton = new Button("Confirmer");

            {
                confirmerButton.setStyle("-fx-background-color: #7CF3D1;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(confirmerButton);
                    confirmerButton.setOnAction(event -> {
                        loadData(); // Reload data after confirmation
                    });
                }
            }
        });
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        prixColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());

        // Make columns editable
        adresseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        adresseColumn.setOnEditCommit(event -> {
            Livraison livraison = event.getRowValue();
            livraison.setAdresse(event.getNewValue());
            livraisonService.update(livraison);
            loadData(); // Reload data after edit
        });
        livraisonTableView.setEditable(true);

        // Add delete buttons to each row
        deleteColumn.setCellFactory(param -> new TableCell
                        <>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #F3977C;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Livraison livraison = getTableView().getItems().get(getIndex());
                        livraisonService.supp(livraison); // Remove from the database
                        getTableView().getItems().remove(livraison); // Remove from the TableView
                        loadData(); // Reload data after deletion
                    });
                }
            }
        });

        // Load data into the table
        loadData();

        // Apply custom styles to TableView and its cells
        livraisonTableView.setStyle("-fx-background-color: #f0f0f0;"); // Set background color
        livraisonTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Resize columns to fit content

        // Apply custom styles to table rows
        livraisonTableView.setRowFactory(tv -> {
            TableRow<Livraison> row = new TableRow<>();
            row.setStyle("-fx-background-color: #ffffff;"); // Set background color for rows
            row.setOnMouseEntered(event -> row.setStyle("-fx-background-color: #f5f5f5;")); // Hover effect
            row.setOnMouseExited(event -> row.setStyle("-fx-background-color: #ffffff;")); // Restore color on exit
            return row;
        });

        // Apply custom styles to table columns
        commandeColumn.setStyle("-fx-alignment: CENTER;");
        adresseColumn.setStyle("-fx-alignment: CENTER;");
        dateColumn.setStyle("-fx-alignment: CENTER;");
        confirmerColumn.setStyle("-fx-alignment: CENTER;");
        emailColumn.setStyle("-fx-alignment: CENTER;");
        prixColumn.setStyle("-fx-alignment: CENTER;");
        deleteColumn.setStyle("-fx-alignment: CENTER;");
        customizeTableHeader(livraisonTableView);

    }
    // Method to customize the header cells of the TableView
    private void customizeTableHeader(TableView<?> tableView) {
        TableHeaderRow headerRow = (TableHeaderRow) tableView.lookup("TableHeaderRow");
        if (headerRow != null) {
            headerRow.setStyle("-fx-background-color: #f0f0f0;"); // Set background color of the header row

            // Apply styles to individual header cells
            for (TableColumn<?, ?> column : tableView.getColumns()) {
                column.setStyle("-fx-background-color: #FFFFFF; " +
                        "-fx-text-fill: #333333; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 14px;"); // Customize font, text color, etc.
            }
        }
    }
    // Rest of the controller methods...

    @FXML
    void onAddButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLivraison.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Wait for the add livraison window to close
            stage.setOnCloseRequest(e -> loadData());

            stage.showAndWait(); // Show the window and wait for it to close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSearchButtonClick(ActionEvent event) {
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            List<Livraison> searchResults = livraisonService.search(searchText);
            livraisonTableView.getItems().setAll(searchResults);
        } else {
            loadData(); // Reload all data if search field is empty
        }
    }

    @FXML
    void onCommandesButtonClick(ActionEvent event) {
        // Implement navigation to commandes view
        showCommandesView();
    }

    @FXML
    void onReturnButtonClick(ActionEvent event) {
        // Get the source node and close its stage
        Node sourceNode = (Node) event.getSource();
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showCommandesView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Commande.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Commandes");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        // Retrieve data from the LivraisonService
        List<Livraison> livraisonList = livraisonService.show();

        // Set the items in the TableView
        livraisonTableView.getItems().setAll(livraisonList);
    }

    @FXML
    void onEditCommit(TableColumn.CellEditEvent<Livraison, ?> event) {
        Livraison editedLivraison = event.getRowValue();
        if (editedLivraison != null) {
            if (event.getSource() == adresseColumn) {
                editedLivraison.setAdresse((String) event.getNewValue());
            } else if (event.getSource() == prixColumn) {
                editedLivraison.setPrix((Double) event.getNewValue());
            } // Add more else if clauses for other columns if needed
            livraisonService.update(editedLivraison);
        }
    }

}

