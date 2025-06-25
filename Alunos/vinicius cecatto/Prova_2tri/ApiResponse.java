package prova2tri;

import java.util.List;

public class ApiResponse {
    public Show show;

    public static class Show {
        public String name;
        public String language;
        public List<String> genres;
        public Rating rating;
        public String status;
        public String premiered;
        public String ended;
        public Network network;
    }

    public static class Rating {
        public Double average;
    }

    public static class Network {
        public String name;
    }
}
