
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Scanner;

import org.json.JSONObject;

public class WeatherApp {

    // chave da api que você pega no site da visual crossing
    private static final String API_KEY = "G8HQ4RGHU24C2NZLML2ZRTQFY";

    // httpclient é o jeito moderno de fazer requisição http no java
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        // lê o nome da cidade que o usuário digitar
        Scanner scanner = new Scanner(System.in);
        System.out.print("digite a cidade: ");
        String cidade = scanner.nextLine();
        scanner.close();

        try {
            // chama a função que busca o clima
            WeatherInfo weather = fetchWeather(cidade);
            if (weather != null) {
                // mostra os dados do clima na tela
                System.out.println("\n🌤️ clima em " + cidade + ":");
                System.out.println("temperatura atual: " + weather.tempAtual + "°c");
                System.out.println("máxima: " + weather.tempMax + "°c | mínima: " + weather.tempMin + "°c");
                System.out.println("condição: " + weather.condicao);
                System.out.println("umidade: " + weather.umidade + "%");
                System.out.println("precipitação: " + weather.precipitacao + " mm");
                System.out.println("vento: " + weather.velVento + " km/h, direção: " + weather.dirVento + "°");
            }
        } catch (Exception e) {
            System.out.println("erro ao buscar dados: " + e.getMessage());
        }
    }

    // função que chama a api e pega os dados do clima
    private static WeatherInfo fetchWeather(String cidade) throws Exception {
        // monta o link da api com a cidade e a chave
        String endpoint = String.format(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
            cidade.replace(" ", "%20"), API_KEY);

        // monta o request http
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .GET()
            .build();

        // envia o request e recebe a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // transforma o json da resposta em objeto
            JSONObject json = new JSONObject(response.body());
            JSONObject today = json.getJSONArray("days").getJSONObject(0);

            // cria e retorna um objeto com os dados do clima
            return new WeatherInfo(
                today.getDouble("temp"),
                today.getDouble("tempmin"),
                today.getDouble("tempmax"),
                today.getDouble("humidity"),
                today.getDouble("precip"),
                today.getDouble("windspeed"),
                today.getDouble("winddir"),
                today.getString("conditions")
            );
        } else {
            System.out.println("erro http: " + response.statusCode());
            return null;
        }
    }

    // estrutura moderna do java pra guardar os dados do clima
    public record WeatherInfo(
        double tempAtual,
        double tempMin,
        double tempMax,
        double umidade,
        double precipitacao,
        double velVento,
        double dirVento,
        String condicao
    ) {}
}
