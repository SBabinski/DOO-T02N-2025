public class UrlBuilder {
    // Método para criar a URL da API do TVmaze
    public static String criarUrl(String nomeSerie) {
        return "https://api.tvmaze.com/search/shows?q=" + nomeSerie;
    }
}
