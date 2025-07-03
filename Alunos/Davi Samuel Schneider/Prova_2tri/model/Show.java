package model;

import java.util.List;

public class Show {
    private String name;
    private String language;
    private List<String> genres;
    private Rating rating;
    private String status;
    private Network network;
    private String premiered;
    private String ended;

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Rating getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public Network getNetwork() {
        return network;
    }

    public String getPremiered() {
        return premiered;
    }

    public String getEnded() {
        return ended;
    }

    @Override
    public String toString() {
        return "Show{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", genres=" + genres +
                ", rating=" + rating +
                ", status='" + status + '\'' +
                ", network=" + network +
                ", premiered=" + premiered +
                ", ended=" + ended +
                '}';
    }
}
