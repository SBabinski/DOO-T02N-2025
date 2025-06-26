package model;

import java.util.Objects;

public class Serie implements Comparable<Serie> {
    private Show show;

    public Show getShow() {
        return show;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return Objects.equals(show, serie.show);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(show);
    }

    @Override
    public String toString() {
        return "" + show;
    }

    @Override
    public int compareTo(Serie o) {
        return show.getName().compareTo(o.show.getName());
    }
}
