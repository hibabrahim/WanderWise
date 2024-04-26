package com.example.hiba;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView img;

    private Fruit fruit;
    private Cart cart; // Inject the Cart instance

    public void setData(Fruit fruit, Cart cart) {
        this.fruit = fruit;
        this.cart = cart;
        priceLabel.setText(Main.CURRENCY + fruit.getPrice());
        String imgSrc = fruit.getImgSrc();
        if (imgSrc != null) {
            img.setImage(new Image(getClass().getResourceAsStream(imgSrc)));
        }
    }

    @FXML
    private void addToCart(MouseEvent mouseEvent) {
        cart.addItem(fruit);// Add the selected fruit to the cart
        System.out.println(cart);
    }

    public void click(MouseEvent mouseEvent) {
    }
}
