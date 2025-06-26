package models;

import java.util.List;

public class Serie {
    private String name;
    private String language;
    private List<String> genres;
    private double rating;
    private String status;
    private Long premiered;  
    private Long ended;      
    private String network;

    // Getters e setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPremiered() {
        return premiered;
    }

    public void setPremiered(Long premiered) {
        this.premiered = premiered;
    }

    public Long getEnded() {
        return ended;
    }

    public void setEnded(Long ended) {
        this.ended = ended;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return name + " (" + (premiered != null ? premiered.toString() : "N/A") + ") - " + status;
    }
}
