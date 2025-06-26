package models;

import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<Serie> favorites = new ArrayList<>();
    private ArrayList<Serie> toWatch = new ArrayList<>();
    private ArrayList<Serie> watching = new ArrayList<>();

    @Override
    public String toString() {
        return "Usuário: " + name;
    }

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Serie> getFavorites() { return favorites; }
    public void setFavorites(ArrayList<Serie> favorites) { this.favorites = favorites; }

    public ArrayList<Serie> getToWatch() { return toWatch; }
    public void setToWatch(ArrayList<Serie> toWatch) { this.toWatch = toWatch; }

    public ArrayList<Serie> getWatching() { return watching; }
    public void setWatching(ArrayList<Serie> watching) { this.watching = watching; }
}
