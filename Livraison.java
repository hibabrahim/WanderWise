package com.example.hiba;

import java.time.LocalDate;

public class Livraison {
    private int id;
    private String commande;
    private String adresse;
    private LocalDate date;
    private boolean confirmer;
    private String email;
    private double prix;

    // Constructor
    public Livraison(String commande, String adresse, LocalDate date, boolean confirmer, String email, double prix) {
        this.id = id;
        this.commande = commande;
        this.adresse = adresse;
        this.date = date;
        this.confirmer = confirmer;
        this.email = email;
        this.prix = prix;
    }

    public Livraison(String adresse, LocalDate date, String email, double prix) {
        this.adresse = adresse;
        this.date = date;
        this.email = email;
        this.prix = prix;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isConfirmer() {
        return confirmer;
    }

    public void setConfirmer(boolean confirmer) {
        this.confirmer = confirmer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
