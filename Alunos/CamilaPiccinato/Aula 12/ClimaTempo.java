import java.net.http.*;
import java.net.URI;
import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.*;

public class ClimaTempo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("\nCidade: ");
                String cidade = scanner.nextLine();
                String key = "QSDWPXNN7P7AAKXYUG2Y3UDA9";


                String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

                
                HttpRequest req = HttpRequest.newBuilder(
                    new URI("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" +
                    cidadeCodificada + "?key=" + key + "&unitGroup=metric&include=current")
                ).build();

                HttpResponse<String> res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());

                
                if (res.statusCode() != 200) {
                    System.out.println("Erro ao obter dados do clima. Código de status: " + res.statusCode());
                    continue; 
                }

        
                JSONObject json = new JSONObject(res.body());
                JSONObject atual = json.getJSONObject("currentConditions");
                JSONObject hoje = json.getJSONArray("days").getJSONObject(0);

               
                double precip = atual.isNull("precip") ? 0.0 : atual.getDouble("precip");

                
                System.out.printf("Cidade: %s | Temp: %.1f°C | Máx: %.1f°C | Mín: %.1f°C | Umidade: %.0f%% | Condição: %s | Precipitação: %.1f mm | Vento: %.1f km/h direção %.0f°%n",
                    json.getString("resolvedAddress"), atual.getDouble("temp"),
                    hoje.getDouble("tempmax"), hoje.getDouble("tempmin"),
                    atual.getDouble("humidity"), atual.getString("conditions"),
                    precip, atual.getDouble("windspeed"), atual.getDouble("winddir"));

                
                System.out.print("Deseja verificar o clima de outra cidade? (s/n): ");
                String resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("n")) {
                    System.out.println("Saindo do programa...");
                    break;  
                }
            } catch (Exception e) {
               
                System.out.println("Ocorreu um erro ao processar a requisição: " + e.getMessage());
                
            }
        }

        scanner.close();  
    }
}