package com.example.hiba;

import java.time.LocalDate;

public class Commande {
    private int id_c;
    private LocalDate date;
    private String statue;
    private double remise;
    private int user_id;

    // Constructor
    public Commande(int id_c, LocalDate date, String statue, double remise, int user_id) {
        this.id_c = id_c;
        this.date = date;
        this.statue = statue;
        this.remise = remise;
        this.user_id = user_id;
    }

    public Commande(String date, String statut, double remise, int userId) {
        this.date = LocalDate.parse(date);
        this.statue= statue;
        this.remise = remise;
        this.user_id = userId;
    }

    // Getters and Setters
    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
