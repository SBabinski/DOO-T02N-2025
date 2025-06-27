package clima;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class AplicativoClima {

    // Coloque aqui sua chave da API Visual Crossing
    private static final String CHAVE_API = "VM5K2S3UX4EBM3D7YR3MPP3QQ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        try {
            String respostaJson = buscarDadosClima(cidade);
            ClimaResposta clima = interpretarRespostaJson(respostaJson);

            System.out.println("\n--- Dados do Clima para " + cidade + " ---");
            System.out.println("Temperatura atual: " + clima.temperaturaAtual + "°C");
            System.out.println("Temperatura máxima: " + clima.temperaturaMaxima + "°C");
            System.out.println("Temperatura mínima: " + clima.temperaturaMinima + "°C");
            System.out.println("Umidade: " + clima.umidade + "%");
            System.out.println("Condição do tempo: " + clima.condicao);
            System.out.println("Precipitação: " + clima.precipitacao + " mm");
            System.out.println("Vento: " + clima.velocidadeVento + " km/h, direção: " + clima.direcaoVento);
        } catch (Exception e) {
            System.out.println("Erro ao buscar dados do clima: " + e.getMessage());
        }

        scanner.close();
    }

    // Método modificado para ignorar a verificação de certificado SSL (para testes)
    private static String buscarDadosClima(String cidade) throws Exception {
        String url = String.format(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
            cidade.replace(" ", "%20"), CHAVE_API);

        // Ignorar verificação de SSL (apenas para testes)
        javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
        sslContext.init(null, new javax.net.ssl.TrustManager[]{
            new javax.net.ssl.X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
            }
        }, new java.security.SecureRandom());

        HttpClient cliente = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

        return resposta.body();
    }

    private static ClimaResposta interpretarRespostaJson(String json) {
        Gson gson = new Gson();
        JsonObject objetoJson = gson.fromJson(json, JsonObject.class);

        JsonArray dias = objetoJson.getAsJsonArray("days");
        JsonObject hoje = dias.get(0).getAsJsonObject();

        ClimaResposta clima = new ClimaResposta();
        clima.temperaturaAtual = hoje.get("temp").getAsDouble();
        clima.temperaturaMaxima = hoje.get("tempmax").getAsDouble();
        clima.temperaturaMinima = hoje.get("tempmin").getAsDouble();
        clima.umidade = hoje.get("humidity").getAsDouble();
        clima.condicao = hoje.get("conditions").getAsString();
        clima.precipitacao = hoje.get("precip").getAsDouble();
        clima.velocidadeVento = hoje.get("windspeed").getAsDouble();
        clima.direcaoVento = hoje.get("winddir").getAsDouble();

        return clima;
    }
}
