import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável pela persistência de dados em JSON
 */
public class PersistenciaJSON {
    private static final String DATA_FILE = "dados_usuario.json";

    /**
     * Salva os dados do usuário em arquivo JSON
     */
    public void salvarUsuario(Usuario usuario) throws IOException {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"nome\": \"").append(escapeJson(usuario.getNome())).append("\",\n");
        json.append("  \"favoritos\": ").append(serializeSeriesList(usuario.getFavoritos())).append(",\n");
        json.append("  \"assistidas\": ").append(serializeSeriesList(usuario.getAssistidas())).append(",\n");
        json.append("  \"paraAssistir\": ").append(serializeSeriesList(usuario.getParaAssistir())).append("\n");
        json.append("}");

        try (FileWriter writer = new FileWriter(DATA_FILE, StandardCharsets.UTF_8)) {
            writer.write(json.toString());
        }
    }

    /**
     * Carrega os dados do usuário do arquivo JSON
     */
    public Usuario carregarUsuario() throws IOException {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return null;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return parseUsuarioJson(content.toString());
    }

    /**
     * Verifica se existe arquivo de dados
     */
    public boolean existeDados() {
        return new File(DATA_FILE).exists();
    }

    /**
     * Serializa uma lista de séries para JSON
     */
    private String serializeSeriesList(List<Serie> series) {
        if (series == null || series.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for (int i = 0; i < series.size(); i++) {
            Serie serie = series.get(i);
            json.append("    ").append(serializeSerie(serie));
            if (i < series.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]");
        return json.toString();
    }

    /**
     * Serializa uma série para JSON
     */
    private String serializeSerie(Serie serie) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("      \"id\": ").append(serie.getId()).append(",\n");
        json.append("      \"nome\": \"").append(escapeJson(serie.getNome())).append("\",\n");
        json.append("      \"idioma\": \"").append(escapeJson(serie.getIdioma())).append("\",\n");
        json.append("      \"generos\": ").append(serializeStringList(serie.getGeneros())).append(",\n");
        json.append("      \"notaGeral\": ").append(serie.getNotaGeral()).append(",\n");
        json.append("      \"estado\": \"").append(escapeJson(serie.getEstado())).append("\",\n");
        json.append("      \"dataEstreia\": \"").append(dateToString(serie.getDataEstreia())).append("\",\n");
        json.append("      \"dataTermino\": \"").append(dateToString(serie.getDataTermino())).append("\",\n");
        json.append("      \"emissora\": \"").append(escapeJson(serie.getEmissora())).append("\",\n");
        json.append("      \"resumo\": \"").append(escapeJson(serie.getResumo())).append("\"\n");
        json.append("    }");
        return json.toString();
    }

    /**
     * Serializa uma lista de strings para JSON
     */
    private String serializeStringList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < list.size(); i++) {
            json.append("\"").append(escapeJson(list.get(i))).append("\"");
            if (i < list.size() - 1) {
                json.append(", ");
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Converte LocalDate para string
     */
    private String dateToString(LocalDate date) {
        return date != null ? date.toString() : "";
    }

    /**
     * Escapa caracteres especiais do JSON
     */
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    /**
     * Faz o parse do JSON do usuário
     */
    private Usuario parseUsuarioJson(String json) {
        Usuario usuario = new Usuario();

        try {
            // Parse do nome
            String nome = extractStringValue(json, "nome");
            if (nome != null) {
                usuario.setNome(nome);
            }

            // Parse das listas
            String favoritosJson = extractArrayValue(json, "favoritos");
            if (favoritosJson != null) {
                usuario.setFavoritos(parseSeriesList(favoritosJson));
            }

            String assistidasJson = extractArrayValue(json, "assistidas");
            if (assistidasJson != null) {
                usuario.setAssistidas(parseSeriesList(assistidasJson));
            }

            String paraAssistirJson = extractArrayValue(json, "paraAssistir");
            if (paraAssistirJson != null) {
                usuario.setParaAssistir(parseSeriesList(paraAssistirJson));
            }

        } catch (Exception e) {
            System.err.println("Erro ao fazer parse dos dados do usuário: " + e.getMessage());
        }

        return usuario;
    }

    /**
     * Faz o parse de uma lista de séries do JSON
     */
    private List<Serie> parseSeriesList(String arrayJson) {
        List<Serie> series = new ArrayList<>();

        // Remove os colchetes
        if (arrayJson.startsWith("[") && arrayJson.endsWith("]")) {
            arrayJson = arrayJson.substring(1, arrayJson.length() - 1).trim();
        }

        if (arrayJson.isEmpty()) {
            return series;
        }

        // Divide os objetos do array
        List<String> serieObjects = splitJsonObjects(arrayJson);

        for (String serieJson : serieObjects) {
            try {
                Serie serie = parseSerieJson(serieJson);
                if (serie != null) {
                    series.add(serie);
                }
            } catch (Exception e) {
                System.err.println("Erro ao fazer parse de série: " + e.getMessage());
            }
        }

        return series;
    }

    /**
     * Faz o parse de uma série do JSON
     */
    private Serie parseSerieJson(String serieJson) {
        Serie serie = new Serie();

        serie.setId(extractIntValue(serieJson, "id"));
        serie.setNome(extractStringValue(serieJson, "nome"));
        serie.setIdioma(extractStringValue(serieJson, "idioma"));
        serie.setEstado(extractStringValue(serieJson, "estado"));
        serie.setEmissora(extractStringValue(serieJson, "emissora"));
        serie.setResumo(extractStringValue(serieJson, "resumo"));
        serie.setNotaGeral(extractDoubleValue(serieJson, "notaGeral"));

        // Parse das datas
        String dataEstreia = extractStringValue(serieJson, "dataEstreia");
        if (dataEstreia != null && !dataEstreia.isEmpty()) {
            serie.setDataEstreia(Serie.parseDate(dataEstreia));
        }

        String dataTermino = extractStringValue(serieJson, "dataTermino");
        if (dataTermino != null && !dataTermino.isEmpty()) {
            serie.setDataTermino(Serie.parseDate(dataTermino));
        }

        // Parse dos gêneros
        String generosJson = extractArrayValue(serieJson, "generos");
        if (generosJson != null) {
            serie.setGeneros(parseStringArray(generosJson));
        }

        return serie;
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
     * Extrai um valor string de um JSON
     */
    private String extractStringValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
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
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(arrayJson);

        while (matcher.find()) {
            result.add(unescapeJson(matcher.group(1)));
        }

        return result;
    }

    /**
     * Remove escape de caracteres JSON
     */
    private String unescapeJson(String text) {
        if (text == null) {
            return null;
        }
        return text.replace("\\\"", "\"")
                  .replace("\\\\", "\\")
                  .replace("\\n", "\n")
                  .replace("\\r", "\r")
                  .replace("\\t", "\t");
    }
}
