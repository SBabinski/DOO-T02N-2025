package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.SerieDTO;

public class ApiService {
    public SerieDTO buscarSerie(String termo) throws Exception {
        String termoEncode = URLEncoder.encode(termo, StandardCharsets.UTF_8.toString());
        String endereco = "https://api.tvmaze.com/singlesearch/shows?q=" + termoEncode;
        URL url = new URL(endereco);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;

        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }

        leitor.close();
        conexao.disconnect();

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(resposta.toString(), JsonObject.class);

        String nome = json.get("name").getAsString();
        String idioma = json.get("language").getAsString();

        JsonArray generosArray = json.getAsJsonArray("genres");
        List<String> generos = gson.fromJson(generosArray, List.class);

        JsonObject rating = json.getAsJsonObject("rating");
        double nota = rating.has("average") && !rating.get("average").isJsonNull()
                ? rating.get("average").getAsDouble()
                : 0.0;

        String status = json.get("status").getAsString();
        String dataEstreia = json.get("premiered").isJsonNull() ? null : json.get("premiered").getAsString();
        String dataFim = json.get("ended").isJsonNull() ? null : json.get("ended").getAsString();

        String emissora = null;
        if (json.has("network") && json.get("network").isJsonObject()) {
            JsonObject network = json.getAsJsonObject("network");
            emissora = network.has("name") ? network.get("name").getAsString() : null;
        }

        return new SerieDTO(nome, idioma, generos, nota, status, dataEstreia, dataFim, emissora);
    }
}