import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class ServicoTVMaze {
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";

    public static List<Serie> buscarSeries(String termo) {
        List<Serie> resultados = new ArrayList<>();

        try {
            URL url = new URL(API_URL + termo.replace(" ", "%20"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            JsonArray jsonArray = JsonParser.parseReader(in).getAsJsonArray();

            for (JsonElement elem : jsonArray) {
                JsonObject show = elem.getAsJsonObject().get("show").getAsJsonObject();

                int id = show.get("id").getAsInt();
                String nome = show.get("name").getAsString();
                String idioma = show.get("language").isJsonNull() ? "N/A" : show.get("language").getAsString();
                JsonArray generosArray = show.get("genres").getAsJsonArray();
                List<String> generos = new ArrayList<>();
                for (JsonElement g : generosArray) generos.add(g.getAsString());
                double nota = show.get("rating").getAsJsonObject().get("average").isJsonNull() ? 0.0 :
                              show.get("rating").getAsJsonObject().get("average").getAsDouble();
                String status = show.get("status").getAsString();
                String estreia = show.get("premiered").isJsonNull() ? "N/A" : show.get("premiered").getAsString();
                String fim = show.get("ended").isJsonNull() ? null : show.get("ended").getAsString();
                String emissora = show.get("network") != null && !show.get("network").isJsonNull() 
                                  ? show.get("network").getAsJsonObject().get("name").getAsString()
                                  : "N/A";

                resultados.add(new Serie(id, nome, idioma, generos, nota, status, estreia, fim, emissora));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
        }

        return resultados;
    }
}
