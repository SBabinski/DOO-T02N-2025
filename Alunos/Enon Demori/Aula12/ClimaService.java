package enon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClimaService {
    
    private static final String API_KEY = "H96F87R99XN82QM79ZBQ5VVKC";

    public static WeatherData obterDadosClimaticos(String cidade) throws Exception {
        String urlStr = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + cidade.replace(" ", "%20")
                + "/today?unitGroup=metric&key=" + API_KEY + "&include=hours&lang=pt"; // a própria api ja vem com tradução para português, ainda bem que achei

        URL url = java.net.URI.create(urlStr).toURL();
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        int status = conexao.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Erro na conexão com a API: " + status);
        }

        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }
        leitor.close();
       // System.out.println("JSON da API:\n" + resposta.toString()); esse é o json que vem da api, usei pra debugar
        return parseJson(resposta.toString());
    }

   private static WeatherData parseJson(String json) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode raiz = mapper.readTree(json);
    JsonNode hoje = raiz.get("days").get(0); 
    JsonNode atual = raiz.get("currentConditions"); 

    WeatherData clima = new WeatherData();

  if (atual != null && !atual.isNull()) {
    clima.setTemperaturaAtual(atual.get("temp").asDouble());
    clima.setUmidade(atual.get("humidity").asDouble());
    clima.setCondicao(atual.has("conditions") ? atual.get("conditions").asText() : "Não informado");
    clima.setPrecipitacao(atual.has("precip") ? atual.get("precip").asDouble() : 0.0);
    clima.setVentoVelocidade(atual.has("windspeed") ? atual.get("windspeed").asDouble() : 0.0);
    clima.setVentoDirecao(atual.has("winddir") ? String.valueOf(atual.get("winddir").asDouble()) : "Não informado");
} else {
    clima.setTemperaturaAtual(hoje.get("temp").asDouble()); 
    clima.setUmidade(hoje.get("humidity").asDouble());
    // Aqui vem a correção:
    clima.setCondicao(hoje.has("conditions") ? hoje.get("conditions").asText() : "Não informado");
    clima.setPrecipitacao(hoje.has("precip") ? hoje.get("precip").asDouble() : 0.0);
    clima.setVentoVelocidade(hoje.has("windspeed") ? hoje.get("windspeed").asDouble() : 0.0);
    clima.setVentoDirecao(hoje.has("winddir") ? String.valueOf(hoje.get("winddir").asDouble()) : "Não informado");
}


    clima.setTemperaturaMaxima(hoje.get("tempmax").asDouble());
    clima.setTemperaturaMinima(hoje.get("tempmin").asDouble());

    return clima;
}

}
