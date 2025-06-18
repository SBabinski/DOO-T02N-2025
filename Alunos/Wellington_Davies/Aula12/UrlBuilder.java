public class UrlBuilder {

    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String PARAMETROS_FIXOS = "?unitGroup=metric&include=current&key=";

    public static String montarUrl(String cidade) {
        String chave = Chave.getChave();

        if (chave == null || chave.isEmpty()) {
            throw new IllegalStateException("Chave da API não definida.");
        }

        // Remove espaços extras da cidade e converte para formato de URL
        String cidadeFormatada = cidade.trim().replace(" ", "%20");

        return BASE_URL + cidadeFormatada + PARAMETROS_FIXOS + chave;
    }
}
