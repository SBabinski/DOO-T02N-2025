import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClimaApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();
        scanner.close();

        String apiKey = "SUA_API_KEY_AQUI"; // substitua pela sua chave real
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                     + cidade + "/today?unitGroup=metric&key=" + apiKey + "&contentType=json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            JSONArray days = json.getJSONArray("days");
            JSONObject today = days.getJSONObject(0);

            double tempAtual = today.getDouble("temp");
            double tempMax = today.getDouble("tempmax");
            double tempMin = today.getDouble("tempmin");
            double umidade = today.getDouble("humidity");
            String condicao = today.getString("conditions");
            double precipitacao = today.getDouble("precip");
            double velocidadeVento = today.getDouble("windspeed");
            String direcaoVento = today.getString("winddir");

            System.out.println("\n--- Clima para " + cidade + " ---");
            System.out.println("Temperatura Atual: " + tempAtual + "°C");
            System.out.println("Máxima do Dia: " + tempMax + "°C");
            System.out.println("Mínima do Dia: " + tempMin + "°C");
            System.out.println("Umidade: " + umidade + "%");
            System.out.println("Condição do Tempo: " + condicao);
            System.out.println("Precipitação: " + precipitacao + "mm");
            System.out.println("Vento: " + velocidadeVento + " km/h - Direção: " + direcaoVento + "°");

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao buscar dados do clima: " + e.getMessage());
        }
    }
}
