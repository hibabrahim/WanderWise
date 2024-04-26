package com.example.hiba;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService implements Interface<Livraison> {
    Connection cnx = Connexion.getConnection();

    @Override
    public void add(Livraison livraison) {
        String query = "INSERT INTO livraison (commande, adresse, date, confirmer, email, prix) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, livraison.getCommande());
            statement.setString(2, livraison.getAdresse());
            statement.setDate(3, Date.valueOf(livraison.getDate()));
            statement.setBoolean(4, livraison.isConfirmer());
            statement.setString(5, livraison.getEmail());
            statement.setDouble(6, livraison.getPrix());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Livraison livraison) {
        String query = "UPDATE livraison SET commande=?, adresse=?, date=?, confirmer=?, email=?, prix=? WHERE id=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, livraison.getCommande());
            statement.setString(2, livraison.getAdresse());
            statement.setDate(3, Date.valueOf(livraison.getDate()));
            statement.setBoolean(4, livraison.isConfirmer());
            statement.setString(5, livraison.getEmail());
            statement.setDouble(6, livraison.getPrix());
            statement.setInt(7, livraison.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supp(Livraison livraison) {
        String query = "DELETE FROM livraison WHERE id=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, livraison.getId());
            statement.executeUpdate();
            System.out.print("supprimé");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Livraison> show() {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison";

        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String commande = resultSet.getString("commande");
                String adresse = resultSet.getString("adresse");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                boolean confirmer = resultSet.getBoolean("confirmer");
                String email = resultSet.getString("email");
                double prix = resultSet.getDouble("prix");

                Livraison livraison = new Livraison(commande, adresse, date, confirmer, email, prix);
                livraisons.add(livraison);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livraisons;
    }

    public List<Livraison> search(String keyword) {
        List<Livraison> searchResults = new ArrayList<>();
        String query = "SELECT * FROM livraison WHERE commande LIKE ? OR adresse LIKE ? OR email LIKE ?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String commande = resultSet.getString("commande");
                    String adresse = resultSet.getString("adresse");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    boolean confirmer = resultSet.getBoolean("confirmer");
                    String email = resultSet.getString("email");
                    double prix = resultSet.getDouble("prix");

                    Livraison livraison = new Livraison(commande, adresse, date, confirmer, email, prix);
                    searchResults.add(livraison);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

}
