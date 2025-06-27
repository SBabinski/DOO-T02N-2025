package com.monitoradeseries.model;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
public class Serie implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String premiered;
    private String status;
    private Rating rating;
    private String summary;
    private List<String> genres; 

    // Construtor
    public Serie(Integer id, String name, String premiered, String status, Rating rating, String summary, List<String> genres) {
        this.id = id;
        this.name = name;
        this.premiered = premiered;
        this.status = status;
        this.rating = rating;
        this.summary = summary;
        this.genres = genres;
    }

    // Getters
    public Integer getId() { 
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPremiered() {
        return premiered;
    }

    public String getStatus() {
        return status;
    }

    public Rating getRating() {
        return rating;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getGenres() { 
        return genres;
    }

   

    @Override
    public String toString() {
        
        String genresDisplay = (genres != null && !genres.isEmpty()) ?
                               String.join(", ", genres) : "N/A";

        return "Título: " + name +
               "\nID: " + id +
               "\nEstreia: " + (premiered != null ? premiered : "N/A") +
               "\nStatus: " + (status != null ? status : "N/A") +
               "\nNota (Média): " + (rating != null && rating.getAverage() != null ? rating.getAverage() : "N/A") +
               "\nGêneros: " + genresDisplay + 
               "\nResumo: " + (summary != null ? summary.replaceAll("<.*?>", "") : "N/A");
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return Objects.equals(id, serie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}