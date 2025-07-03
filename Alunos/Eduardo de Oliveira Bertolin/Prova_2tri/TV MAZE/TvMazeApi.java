import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import com.google.gson.*;

// buscar séries na API 
public class TvMazeApi {
    // URL
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";

    // busca séries pelo nome e retorna uma lista de objetos Serie
    public static List<Serie> buscarSeriesPorNome(String nome) {

        try {
            // URL de busca pra evitar erros em caracteres especiais
            String urlStr = API_URL + URLEncoder.encode(nome, "UTF-8");
            URL url = new URL(urlStr);

            // abre a conexão e lê a resposta da API
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                
                // converte a resposta JSON em um array de elementos
                JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
                List<Serie> resultado = new ArrayList<>();

                // para cada elemento retornado, extrai os dados da série
                for (JsonElement elem : array) {
                    JsonObject show = elem.getAsJsonObject().getAsJsonObject("show");
                    int id = show.get("id").getAsInt();
                    String showNome = show.get("name").getAsString();
                    String idioma = show.has("language") && !show.get("language").isJsonNull()
                            ? show.get("language").getAsString()
                            : "";
                    List<String> generos = new ArrayList<>();
                    JsonArray genresArr = show.getAsJsonArray("genres");
                    for (JsonElement g : genresArr)
                        generos.add(g.getAsString());

                    // verificanota geral
                    double nota = show.has("rating") && show.getAsJsonObject("rating").has("average")
                            && !show.getAsJsonObject("rating").get("average").isJsonNull()
                                    ? show.getAsJsonObject("rating").get("average").getAsDouble()
                                    : 0.0;
                    String estado = show.has("status") && !show.get("status").isJsonNull()
                            ? show.get("status").getAsString()
                            : "";
                    String estreia = show.has("premiered") && !show.get("premiered").isJsonNull()
                            ? show.get("premiered").getAsString()
                            : "";
                    String termino = show.has("ended") && !show.get("ended").isJsonNull() ? show.get("ended").getAsString()
                            : "";
                    // emissora pode estar em "network" ou "webChannel"
                    String emissora = "";
                    if (show.has("network") && !show.get("network").isJsonNull()) {
                        emissora = show.getAsJsonObject("network").get("name").getAsString();
                    } else if (show.has("webChannel") && !show.get("webChannel").isJsonNull()) {
                        emissora = show.getAsJsonObject("webChannel").get("name").getAsString();
                    }

                    // img
                    String imagemUrl = "";
                    if (show.has("image") && !show.get("image").isJsonNull()) {
                        JsonObject imageObj = show.getAsJsonObject("image");
                        if (imageObj.has("medium") && !imageObj.get("medium").isJsonNull()) {
                            imagemUrl = imageObj.get("medium").getAsString();
                        } else if (imageObj.has("original") && !imageObj.get("original").isJsonNull()) {
                            imagemUrl = imageObj.get("original").getAsString();
                        }
                    }

                    // cria um novo objeto Serie com todos os dados coletados e adiciona à lista de resultados
                    resultado.add(
                            new Serie(id, showNome, idioma, generos, nota, estado, estreia, termino, emissora, imagemUrl));
                }
                // retorna a lista de séries encontradas
                return resultado;
            }
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Sem conexão com a internet. Verifique sua rede.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro de comunicação com o serviço. Tente novamente mais tarde.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado ao buscar séries: " + e.getMessage());
        }
        // retorna lista vazia em caso de erro
        return Collections.emptyList();
    }
}
