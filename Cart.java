package com.example.hiba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
    private ObservableList<Fruit> items;

    public Cart() {
        this.items = FXCollections.observableArrayList(); // Initialize the ObservableList here
    }

    public void addItem(Fruit item) {
        this.items.add(item);
    }

    // Other methods...

    public ObservableList<Fruit> getItems() {
        return items;
    }

    // Other methods...
}
