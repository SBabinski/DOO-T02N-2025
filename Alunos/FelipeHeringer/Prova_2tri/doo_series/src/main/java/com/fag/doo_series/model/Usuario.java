package com.fag.doo_series.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class Usuario {
    private String name;
    private String cpf;
    private List<Object> favoriteGenres;
    private ArrayList<Series> favoriteSeries;
    private ArrayList<Series> seriesWatchLater;
    private ArrayList<Series> seriesWatched;

    public Usuario(String name, String cpf, List<Object> favoritesGenres) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode estar vazio");
        }

        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("O cpf deve estar de acordo com o formato XXX.XXX.XXX-XX");
        }

        this.name = name;
        this.cpf = cpf;

        if (favoritesGenres.isEmpty() || favoritesGenres == null) {
            this.favoriteGenres = new ArrayList<>();
        } else {
            this.favoriteGenres = favoritesGenres;
        }
        this.favoriteSeries = new ArrayList<>();
        this.seriesWatchLater = new ArrayList<>();
        this.seriesWatched = new ArrayList<>();
    }

    public Usuario(Document doc) {
        this.name = doc.getString("user_name");
        this.cpf = doc.getString("user_cpf");
        this.favoriteGenres = doc.getList("user_favorite_genres", Object.class);
        if (this.favoriteGenres == null) {
            this.favoriteGenres = new ArrayList<>();
        }

        List<Document> favDocs = doc.getList("favorite_series", Document.class);
        this.favoriteSeries = new ArrayList<>();
        if (favDocs != null) {
            for (Document d : favDocs) {
                this.favoriteSeries.add(new Series(d));
            }
        }

        List<Document> watchLaterDocs = doc.getList("series_watch_later", Document.class);
        this.seriesWatchLater = new ArrayList<>();
        if (watchLaterDocs != null) {
            for (Document d : watchLaterDocs) {
                this.seriesWatchLater.add(new Series(d));
            }
        }

        List<Document> watchedDocs = doc.getList("series_watched", Document.class);
        this.seriesWatched = new ArrayList<>();
        if (watchedDocs != null) {
            for (Document d : watchedDocs) {
                this.seriesWatched.add(new Series(d));
            }
        }
    }

    public Map<String, Object> toJson() {
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("user_name", name);
        userJson.put("user_cpf", cpf);
        userJson.put("user_favorite_genres", favoriteGenres);
        userJson.put("favorite_series", favoriteSeries);
        userJson.put("series_watch_later", seriesWatchLater);
        userJson.put("series_watched", seriesWatched);
        return userJson;
    }

    public void addSeriesOnfavoriteList(Series series) {
        favoriteSeries.add(series);
    }

    public void addSeriesOnWatchLaterList(Series series) {
        seriesWatchLater.add(series);
    }

    public void addSeriesOnWatchedList(Series series) {
        seriesWatched.add(series);
    }

    public void removeSeriesOnfavoriteList(Series series){
        favoriteSeries.remove(series);
    }

    public void removeSeriesOnWatchLaterList(Series series){
        seriesWatchLater.remove(series);
    }

    public void removeSeriesOnWatchedList(Series series){
        seriesWatched.remove(series);
    }


    public String getCpf() {
        return cpf;
    }

    public List<Object> getFavoriteGenres() {
        return favoriteGenres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Series> getFavoriteSeries() {
        return favoriteSeries;
    }

    public ArrayList<Series> getSeriesWatchLater() {
        return seriesWatchLater;
    }

    public ArrayList<Series> getSeriesWatched() {
        return seriesWatched;
    }

        

}
