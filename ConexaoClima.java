import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexaoClima {
    public String buscarClimaJson(String cidade, String apiKey) throws Exception {
        String urlString = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + cidade + "?unitGroup=metric&key=" + apiKey + "&contentType=json";

        URL url = new URL(urlString);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(5000);
        conexao.setReadTimeout(5000);

        int status = conexao.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Erro ao conectar: HTTP " + status);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            resposta.append(linha);
        }

        reader.close();
        conexao.disconnect();

        return resposta.toString();
    }
}
