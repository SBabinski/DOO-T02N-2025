package Prova_2tri;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.*;

public class SerieAPI {
    private static final String BASE_URL = "https://api.tvmaze.com/";

    public static List<Serie> buscarSeriePorNome(String nome) throws Exception {
        List<Serie> series = new ArrayList<>();
        String urlStr = BASE_URL + "search/shows?q=" + nome.replace(" ", "%20");
        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder resposta = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String linha;
            while ((linha = in.readLine()) != null) {
                resposta.append(linha);
            }
        }
        JSONArray results = new JSONArray(resposta.toString());

        for (int i = 0; i < results.length(); i++) {
            JSONObject showObj = results.getJSONObject(i).getJSONObject("show");
            series.add(jsonToSerie(showObj));
        }
        return series;
    }

    public static Serie buscarSeriePorId(int id) throws Exception {
        String urlStr = BASE_URL + "shows/" + id;
        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder resposta = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String linha;
            while ((linha = in.readLine()) != null) {
                resposta.append(linha);
            }
        }
        JSONObject showObj = new JSONObject(resposta.toString());
        return jsonToSerie(showObj);
    }

    private static Serie jsonToSerie(JSONObject showObj) {
        int id = showObj.optInt("id");
        String nome = showObj.optString("name");
        String idioma = showObj.optString("language");
        List<String> generos = new ArrayList<>();
        JSONArray generosArr = showObj.optJSONArray("genres");
        if (generosArr != null) {
            for (int j = 0; j < generosArr.length(); j++) generos.add(generosArr.getString(j));
        }
        double notaGeral = showObj.optJSONObject("rating") != null ? showObj.getJSONObject("rating").optDouble("average", 0.0) : 0.0;
        String estado = showObj.optString("status");
        String dataEstreia = showObj.optString("premiered");
        String dataTermino = showObj.optString("ended");
        String emissora = "";
        if (!showObj.isNull("network")) {
            JSONObject network = showObj.getJSONObject("network");
            emissora = network.optString("name", "");
        } else if (!showObj.isNull("webChannel")) {
            JSONObject webChannel = showObj.getJSONObject("webChannel");
            emissora = webChannel.optString("name", "");
        }
        return new Serie(id, nome, idioma, generos, notaGeral, estado, dataEstreia, dataTermino, emissora);
    }
}