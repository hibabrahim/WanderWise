package com.example.hiba;

import com.example.hiba.Connexion;
import com.example.hiba.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    Connection cnx = Connexion.getConnection();

    public void addCartItem(Fruit fruit, int userId) {
        String query = "INSERT INTO cart_item ( fruit_price, user_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setDouble(2, fruit.getPrice());
            statement.setInt(3, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCartItem(int cartItemId) {
        String query = "DELETE FROM cart_item WHERE id=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, cartItemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Fruit> getCartItems(int userId) {
        List<Fruit> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart_item WHERE user_id=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("fruit_name");
                    double price = resultSet.getDouble("fruit_price");

                    Fruit fruit = new Fruit();
                    fruit.setName(name);
                    fruit.setPrice(price);

                    cartItems.add(fruit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
}
