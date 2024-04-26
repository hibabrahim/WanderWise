package com.example.hiba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<Fruit> fruits = new ArrayList<>();
    private Cart cart; // Inject the Cart instance
    @FXML
    private ListView<Fruit> cartListView;
    private CartService cartService; // Instance of CartService

    private List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        fruit = new Fruit();
        fruit.setName("Kiwi");
        fruit.setPrice(2.99);
        fruit.setColor("6A7324");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Coconut");
        fruit.setPrice(3.99);
        fruit.setColor("A7745B");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("Peach");
        fruit.setPrice(1.50);
        fruit.setColor("F16C31");
        fruits.add(fruit);

        // Add other fruits similarly...

        return fruits;
    }
    private Fruit initializeStaticItem() {
        Fruit staticItem = new Fruit();
        staticItem.setName("Static Item");
        staticItem.setPrice(5.99); // Set the price as desired
        staticItem.setColor("CCCCCC"); // Set the color code if needed
        return staticItem;
    }

    // Method to add the static item to the cart
    public void addStaticItemToCart() {
        Fruit staticItem = initializeStaticItem();
        cart.addItem(staticItem); // Add the static item to the cart
    }
    private void setChosenFruit(Fruit fruit) {
        fruitPriceLabel.setText(Main.CURRENCY + fruit.getPrice());
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }
    public ListCell<String> createCartCell(ListView<String> listView) {
        return new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        };
    }
    private void updateCartListView() {
        // Get the list of added fruits from the cart
        List<Fruit> addedFruits = cart.getItems();

        // Update the items in the cartListView
        cartListView.setItems(FXCollections.observableArrayList(addedFruits));

        // Add static item to cart
        addStaticItemToCart();

        System.out.println(addedFruits);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartService = new CartService();

        fruits.addAll(getData());
        if (!fruits.isEmpty()) {
            setChosenFruit(fruits.get(0));
        }

        // Ensure that cart is initialized before updating the cart list view
        cart = new Cart(); // Initialize cart here if not injected

        // Set padding and gaps for the grid
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        loadItems();

        // Initialize cartListView with an ObservableList of items
        ObservableList<Fruit> cartItems = FXCollections.observableArrayList();
        cartItems.addAll(cart.getItems());
        cartListView.setItems(cartItems);

        // Add static item to cart
        addStaticItemToCart();
    }


    private void loadItems() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < Math.min(3, fruits.size()); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i), (Cart) cart); // Pass the cart instance

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItem(Fruit fruit) {
        // Create a new item for the added fruit
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            ItemController itemController = fxmlLoader.getController();
            itemController.setData(fruit, (Cart) cart); // Pass the cart instance

            int numRows = grid.getRowCount();
            int column = (grid.getChildren().size() % 3); // 3 items per row
            int row = (grid.getChildren().size() / 3) + 1;

            grid.add(anchorPane, column, row); //(child,column,row)

            // Update the preferred height of the grid
            grid.setPrefHeight(numRows * 200); // Adjust the height based on item height

            // Add fruit to the cart
            cart.addItem(fruit);

            // Save the fruit to the database using CartService
            saveFruitToDatabase(fruit);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFruitToDatabase(Fruit fruit) {
        // Assuming you have a user ID
        int userId = 1; // Change this according to your application logic

        // Add the fruit to the database using CartService
        cartService.addCartItem(fruit, userId);
    }
}