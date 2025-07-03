public class Serie {
    String name;
    String language;
    String[] genres;
    String premiered;
    String ended;
    String status;
    Rating rating;
    Network network;

    class Rating {
        Double average;

    }

    class Network {
        String name;
        
    }
}
