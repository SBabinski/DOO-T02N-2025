package sistemaSerie;

import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ApiTvmaze {

    public Serie buscarSeriePorNome(String nome) throws Exception {
        String urlStr = "https://api.tvmaze.com/singlesearch/shows?q=" + nome.replace(" ", "+");
        HttpURLConnection conexao = (HttpURLConnection) new URL(urlStr).openConnection();
        conexao.setRequestMethod("GET");

        if (conexao.getResponseCode() == 200) {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(conexao.getInputStream())).getAsJsonObject();

            int id = json.get("id").getAsInt();
            String titulo = json.get("name").getAsString();
            String idioma = json.get("language").getAsString();

            List<String> generos = new ArrayList<>();
            for (JsonElement e : json.getAsJsonArray("genres")) {
                generos.add(e.getAsString());
            }

            double nota = json.getAsJsonObject("rating").get("average").isJsonNull() ? 0.0 :
                    json.getAsJsonObject("rating").get("average").getAsDouble();

            String status = json.get("status").getAsString();
            String dataEstreia = json.get("premiered").isJsonNull() ? "N/A" : json.get("premiered").getAsString();
            String dataFim = json.get("ended").isJsonNull() ? "N/A" : json.get("ended").getAsString();

            String emissora = "N/A";
            if (json.has("network") && json.get("network").isJsonObject()) {
                emissora = json.getAsJsonObject("network").get("name").getAsString();
            }

            return new Serie(id, titulo, idioma, generos, nota, status, dataEstreia, dataFim, emissora);
        } else {
            throw new Exception("Erro ao buscar série: " + conexao.getResponseCode());
        }
    }
}
