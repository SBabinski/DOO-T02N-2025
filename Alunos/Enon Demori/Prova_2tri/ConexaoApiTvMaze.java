package enon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConexaoApiTvMaze {

    public static List<SerieData> buscarSeries(String nomeSerie) {
        List<SerieData> seriesEncontradas = new ArrayList<>();

        try {
            java.net.URI uri = new java.net.URI(
                    "https",
                    "api.tvmaze.com",
                    "/search/shows",
                    "q=" + nomeSerie.trim().replace(" ", "+"),
                    null);
            URL url = uri.toURL();
            System.out.println("URL gerada para Debug: " + url);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder respostaJson = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                respostaJson.append(linha);
            }
            leitor.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode raiz = mapper.readTree(respostaJson.toString());

            for (JsonNode item : raiz) {
                JsonNode show = item.get("show");
                SerieData serie = new SerieData();

                // Nome
                JsonNode nomeNode = show.get("name");
                serie.setNome((nomeNode != null && !nomeNode.isNull()) ? nomeNode.asText("") : "");

                // Idioma
                JsonNode idiomaNode = show.get("language");
                serie.setIdioma((idiomaNode != null && !idiomaNode.isNull()) ? idiomaNode.asText("") : "");

                // Nota geral
                double nota = 0.0;
                JsonNode ratingNode = show.get("rating");
                if (ratingNode != null && !ratingNode.isNull()) {
                    JsonNode averageNode = ratingNode.get("average");
                    if (averageNode != null && !averageNode.isNull()) {
                        nota = averageNode.asDouble(0.0);
                    }
                }
                serie.setNotaGeral(nota);

                // Estado
                JsonNode estadoNode = show.get("status");
                serie.setEstado((estadoNode != null && !estadoNode.isNull()) ? estadoNode.asText("") : "");

                // Data estreia
                JsonNode estreiaNode = show.get("premiered");
                serie.setDataEstreia((estreiaNode != null && !estreiaNode.isNull()) ? estreiaNode.asText("") : "");

                // Data fim
                JsonNode fimNode = show.get("ended");
                serie.setDataFim((fimNode != null && !fimNode.isNull()) ? fimNode.asText("") : "");

                // Emissora
                String emissora = "Indefinido";
                JsonNode networkNode = show.get("network");
                if (networkNode != null && !networkNode.isNull()) {
                    JsonNode nameNode = networkNode.get("name");
                    if (nameNode != null && !nameNode.isNull()) {
                        emissora = nameNode.asText("");
                    }
                }
                serie.setEmissora(emissora);

                // Gêneros
                List<String> generos = new ArrayList<>();
                JsonNode generosNode = show.get("genres");
                if (generosNode != null && generosNode.isArray()) {
                    for (JsonNode genero : generosNode) {
                        generos.add(genero.asText());
                    }
                }
                serie.setGeneros(generos);

                seriesEncontradas.add(serie);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar série na API:");
            e.printStackTrace();
        }

        return seriesEncontradas;
    }
}