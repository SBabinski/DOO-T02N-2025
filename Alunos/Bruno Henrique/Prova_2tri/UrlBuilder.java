public class UrlBuilder {
    public static String criarUrl(String nomeSerie) {
        String nomeSerieFormatado = nomeSerie.replace(" ", "+");
        return "https://api.tvmaze.com/search/shows?q=" + nomeSerieFormatado;
    }
}