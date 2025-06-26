import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável pela comunicação com a API do TVMaze
 */
public class TVMazeAPI {
    private static final String BASE_URL = "https://api.tvmaze.com";
    private static final String SEARCH_ENDPOINT = "/search/shows";
    private static final String SHOW_ENDPOINT = "/shows";

    /**
     * Busca séries por nome
     */
    public List<Serie> buscarSeries(String nome) throws APIException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new APIException("Nome da série não pode estar vazio");
        }

        try {
            String encodedName = URLEncoder.encode(nome.trim(), StandardCharsets.UTF_8);
            String urlString = BASE_URL + SEARCH_ENDPOINT + "?q=" + encodedName;

            String jsonResponse = makeRequest(urlString);
            return parseSearchResponse(jsonResponse);

        } catch (IOException e) {
            throw new APIException("Erro de conexão com a API: " + e.getMessage());
        } catch (Exception e) {
            throw new APIException("Erro ao buscar séries: " + e.getMessage());
        }
    }

    /**
     * Busca uma série específica por ID
     */
    public Serie buscarSeriePorId(int id) throws APIException {
        try {
            String urlString = BASE_URL + SHOW_ENDPOINT + "/" + id;
            String jsonResponse = makeRequest(urlString);
            return parseShowResponse(jsonResponse);

        } catch (IOException e) {
            throw new APIException("Erro de conexão com a API: " + e.getMessage());
        } catch (Exception e) {
            throw new APIException("Erro ao buscar série: " + e.getMessage());
        }
    }

    /**
     * Faz uma requisição HTTP GET
     */
    private String makeRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "SerieTracker/1.0");
            connection.setConnectTimeout(10000); // 10 segundos
            connection.setReadTimeout(15000);    // 15 segundos

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Erro na API: código " + responseCode);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            return response.toString();

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Faz o parse da resposta de busca (array de objetos)
     */
    private List<Serie> parseSearchResponse(String jsonResponse) {
        List<Serie> series = new ArrayList<>();

        // Remove espaços e quebras de linha
        jsonResponse = jsonResponse.trim();

        // Se a resposta estiver vazia ou for um array vazio
        if (jsonResponse.isEmpty() || jsonResponse.equals("[]")) {
            return series;
        }

        // Remove os colchetes do array principal
        if (jsonResponse.startsWith("[") && jsonResponse.endsWith("]")) {
            jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
        }

        // Divide os objetos do array
        List<String> showObjects = splitJsonObjects(jsonResponse);

        for (String showObject : showObjects) {
            try {
                // Extrai o objeto "show" de cada resultado da busca
                String showJson = extractShowFromSearchResult(showObject);
                if (showJson != null) {
                    Serie serie = parseShowJson(showJson);
                    if (serie != null) {
                        series.add(serie);
                    }
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar resultado da busca: " + e.getMessage());
            }
        }

        return series;
    }

    /**
     * Faz o parse da resposta de uma série específica
     */
    private Serie parseShowResponse(String jsonResponse) {
        return parseShowJson(jsonResponse);
    }

    /**
     * Extrai o objeto "show" de um resultado de busca
     */
    private String extractShowFromSearchResult(String searchResult) {
        Pattern pattern = Pattern.compile("\"show\"\\s*:\\s*\\{");
        Matcher matcher = pattern.matcher(searchResult);

        if (matcher.find()) {
            int start = matcher.start() + matcher.group().indexOf('{');
            int braceCount = 1;
            int i = start + 1;

            while (i < searchResult.length() && braceCount > 0) {
                char c = searchResult.charAt(i);
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                }
                i++;
            }

            if (braceCount == 0) {
                return searchResult.substring(start, i);
            }
        }

        return null;
    }

    /**
     * Divide uma string JSON em objetos separados
     */
    private List<String> splitJsonObjects(String jsonArray) {
        List<String> objects = new ArrayList<>();

        if (jsonArray.trim().isEmpty()) {
            return objects;
        }

        int start = 0;
        int braceCount = 0;
        boolean inString = false;
        boolean escaped = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            char c = jsonArray.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == '"') {
                inString = !inString;
                continue;
            }

            if (!inString) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        objects.add(jsonArray.substring(start, i + 1));
                        start = i + 1;
                        // Pula vírgulas e espaços
                        while (start < jsonArray.length() &&
                               (jsonArray.charAt(start) == ',' ||
                                Character.isWhitespace(jsonArray.charAt(start)))) {
                            start++;
                        }
                        i = start - 1;
                    }
                }
            }
        }

        return objects;
    }

    /**
     * Faz o parse de um objeto JSON de série
     */
    private Serie parseShowJson(String showJson) {
        try {
            Serie serie = new Serie();

            // Parse dos campos principais
            serie.setId(extractIntValue(showJson, "id"));
            serie.setNome(extractStringValue(showJson, "name"));
            serie.setIdioma(extractStringValue(showJson, "language"));
            serie.setEstado(extractStringValue(showJson, "status"));
            serie.setResumo(extractStringValue(showJson, "summary"));

            // Parse da nota (rating)
            String ratingSection = extractObjectValue(showJson, "rating");
            if (ratingSection != null) {
                double rating = extractDoubleValue(ratingSection, "average");
                serie.setNotaGeral(rating);
            }

            // Parse das datas
            String premiered = extractStringValue(showJson, "premiered");
            if (premiered != null) {
                serie.setDataEstreia(Serie.parseDate(premiered));
            }

            String ended = extractStringValue(showJson, "ended");
            if (ended != null) {
                serie.setDataTermino(Serie.parseDate(ended));
            }

            // Parse da emissora (network)
            String networkSection = extractObjectValue(showJson, "network");
            if (networkSection != null) {
                String networkName = extractStringValue(networkSection, "name");
                serie.setEmissora(networkName);
            }

            // Parse dos gêneros
            String genresSection = extractArrayValue(showJson, "genres");
            if (genresSection != null) {
                List<String> genres = parseStringArray(genresSection);
                serie.setGeneros(genres);
            }

            return serie;

        } catch (Exception e) {
            System.err.println("Erro ao fazer parse da série: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extrai um valor string de um JSON
     */
    private String extractStringValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*?)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Extrai um valor inteiro de um JSON
     */
    private int extractIntValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    /**
     * Extrai um valor double de um JSON
     */
    private double extractDoubleValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0.0;
    }

    /**
     * Extrai um objeto de um JSON
     */
    private String extractObjectValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\\{");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            int start = matcher.start() + matcher.group().indexOf('{');
            int braceCount = 1;
            int i = start + 1;

            while (i < json.length() && braceCount > 0) {
                char c = json.charAt(i);
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                }
                i++;
            }

            if (braceCount == 0) {
                return json.substring(start, i);
            }
        }

        return null;
    }

    /**
     * Extrai um array de um JSON
     */
    private String extractArrayValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\\[");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            int start = matcher.start() + matcher.group().indexOf('[');
            int bracketCount = 1;
            int i = start + 1;
            boolean inString = false;
            boolean escaped = false;

            while (i < json.length() && bracketCount > 0) {
                char c = json.charAt(i);

                if (escaped) {
                    escaped = false;
                    i++;
                    continue;
                }

                if (c == '\\') {
                    escaped = true;
                    i++;
                    continue;
                }

                if (c == '"') {
                    inString = !inString;
                } else if (!inString) {
                    if (c == '[') {
                        bracketCount++;
                    } else if (c == ']') {
                        bracketCount--;
                    }
                }
                i++;
            }

            if (bracketCount == 0) {
                return json.substring(start, i);
            }
        }

        return null;
    }

    /**
     * Faz o parse de um array de strings JSON
     */
    private List<String> parseStringArray(String arrayJson) {
        List<String> result = new ArrayList<>();

        // Remove os colchetes
        if (arrayJson.startsWith("[") && arrayJson.endsWith("]")) {
            arrayJson = arrayJson.substring(1, arrayJson.length() - 1);
        }

        if (arrayJson.trim().isEmpty()) {
            return result;
        }

        // Divide por vírgulas, considerando strings
        Pattern pattern = Pattern.compile("\"([^\"]*?)\"");
        Matcher matcher = pattern.matcher(arrayJson);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }
}
