package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    List<Serie> favoriteSeries = new ArrayList<>();
    List<Serie> watchedSeries = new ArrayList<>();
    List<Serie> watchLaterSeries = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public List<Serie> getFavoriteSeries() {
        return favoriteSeries;
    }

    public void setFavoriteSeries(List<Serie> favoriteSeries) {
        this.favoriteSeries = favoriteSeries;
    }

    public List<Serie> getWatchedSeries() {
        return watchedSeries;
    }

    public void setWatchedSeries(List<Serie> watchedSeries) {
        this.watchedSeries = watchedSeries;
    }

    public List<Serie> getWatchLaterSeries() {
        return watchLaterSeries;
    }

    public void setWatchLaterSeries(List<Serie> watchLaterSeries) {
        this.watchLaterSeries = watchLaterSeries;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "favoriteSeries=" + favoriteSeries +
                ", watchedSeries=" + watchedSeries +
                ", watchLaterSeries=" + watchLaterSeries +
                '}';
    }
}
