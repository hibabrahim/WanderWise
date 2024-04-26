package com.example.hiba;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements Interface<Commande> {
    Connection cnx = Connexion.getConnection();

    @Override
    public void add(Commande commande) {
        String query = "INSERT INTO commande (date, statue, remise, user_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(commande.getDate()));
            statement.setString(2, commande.getStatue());
            statement.setDouble(3, commande.getRemise());
            statement.setInt(4, commande.getUser_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Commande commande) {
        String query = "UPDATE commande SET date=?, statue=?, remise=?, user_id=? WHERE id_c=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(commande.getDate()));
            statement.setString(2, commande.getStatue());
            statement.setDouble(3, commande.getRemise());
            statement.setInt(4, commande.getUser_id());
            statement.setInt(5, commande.getId_c());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supp(Commande commande) {
        String query = "DELETE FROM commande WHERE id_c=?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, commande.getId_c());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Commande> show() {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande";

        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id_c = resultSet.getInt("id_c");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String statue = resultSet.getString("statue");
                double remise = resultSet.getDouble("remise");
                int user_id = resultSet.getInt("user_id");

                Commande commande = new Commande(id_c, date, statue, remise, user_id);
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    public List<Commande> search(String searchText) {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande WHERE date LIKE ? OR statue LIKE ? OR remise LIKE ? OR user_id LIKE ?";
        String searchPattern = "%" + searchText + "%";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            for (int i = 1; i <= 4; i++) {
                statement.setString(i, searchPattern);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_c = resultSet.getInt("id_c");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    String statue = resultSet.getString("statue");
                    double remise = resultSet.getDouble("remise");
                    int user_id = resultSet.getInt("user_id");

                    Commande commande = new Commande(id_c, date, statue, remise, user_id);
                    commandes.add(commande);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

}
