import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class WeatherApiService {

    private final String apiKey;
    private final HttpService httpService;
    private final Gson gson;

    public WeatherApiService(String apiKey) {
        this.apiKey = apiKey;
        this.httpService = new HttpService();
        this.gson = new Gson();
    }

    public WeatherResponse getWeatherForCity(String city) {
        try {
            String url = UrlBuilder.build(city, this.apiKey);
            String jsonResponse = httpService.get(url);

            if (jsonResponse == null || jsonResponse.isEmpty()) {
                System.out.println("Erro: A API não retornou dados. Verifique a cidade ou sua chave da API.");
                return null;
            }

            return gson.fromJson(jsonResponse, WeatherResponse.class);

        } catch (JsonSyntaxException e) {
            System.err.println("Erro: A resposta da API não é um JSON válido. Resposta: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados do clima: " + e.getMessage());
            return null;
        }
    }
}