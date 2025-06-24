import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConexaoTVMaze {

    public static List<Serie> buscarSeries(String nomeSerie) {
        List<Serie> resultados = new ArrayList<>();
        try {
            String urlString = "https://api.tvmaze.com/search/shows?q=" +
                    URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            if (conexao.getResponseCode() == 200) {
                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                JsonArray array = JsonParser.parseReader(leitor).getAsJsonArray();

                Gson gson = new Gson();
                for (JsonElement elemento : array) {
                    JsonObject obj = elemento.getAsJsonObject();
                    JsonObject show = obj.getAsJsonObject("show");
                    Serie serie = gson.fromJson(show, Serie.class);
                    resultados.add(serie);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar série: " + e.getMessage());
        }
        return resultados;
    }
}
