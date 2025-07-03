import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Serviço responsável por fazer requisições à API da Visual Crossing
 * e converter as respostas em objetos WeatherData
 *
 * @author Charles Müller
 * @version 1.0
 */
public class WeatherService {
    private static final Logger LOGGER = Logger.getLogger(WeatherService.class.getName());
    private static final String DEFAULT_BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String DEFAULT_ELEMENTS = "temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir";

    private final String apiKey;
    private final ConfigManager configManager;

    /**
     * Construtor que recebe a API key
     */
    public WeatherService(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API Key não pode estar vazia");
        }
        this.apiKey = apiKey.trim();
        this.configManager = ConfigManager.getInstance();
        LOGGER.info("WeatherService inicializado com sucesso");
    }

    /**
     * Obtém os dados meteorológicos atuais para uma cidade
     *
     * @param cidade Nome da cidade (ex: "São Paulo, SP" ou "London, UK")
     * @return WeatherData com as informações meteorológicas
     * @throws WeatherServiceException Se houver erro na consulta
     */
    public WeatherData getCurrentWeather(String cidade) throws WeatherServiceException {
        validateInput(cidade);

        try {
            String url = buildUrl(cidade);
            LOGGER.info("Fazendo requisição para: " + url);

            String jsonResponse = makeHttpRequest(url);

            if (jsonResponse == null || jsonResponse.isEmpty()) {
                throw new WeatherServiceException("Resposta vazia da API");
            }

            return parseWeatherData(jsonResponse, cidade);

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Erro de comunicação com a API", e);
            throw new WeatherServiceException("Erro de comunicação com o serviço meteorológico: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro inesperado no serviço", e);
            throw new WeatherServiceException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    /**
     * Valida os dados de entrada
     */
    private void validateInput(String cidade) throws WeatherServiceException {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new WeatherServiceException("Nome da cidade não pode estar vazio");
        }

        if (cidade.length() > 100) {
            throw new WeatherServiceException("Nome da cidade muito longo");
        }
    }

    /**
     * Constrói a URL da requisição
     */
    private String buildUrl(String cidade) throws WeatherServiceException {
        try {
            String encodedCity = URLEncoder.encode(cidade.trim(), StandardCharsets.UTF_8);
            String baseUrl = configManager.getBaseUrl();
            String unitGroup = configManager.getUnitGroup();
            String language = configManager.getLanguage();

            return baseUrl + encodedCity +
                    "?key=" + apiKey +
                    "&unitGroup=" + unitGroup +
                    "&include=current,days" +
                    "&elements=" + DEFAULT_ELEMENTS +
                    "&lang=" + language;

        } catch (Exception e) {
            throw new WeatherServiceException("Erro ao construir URL da requisição", e);
        }
    }

    /**
     * Faz a requisição HTTP para a API
     */
    private String makeHttpRequest(String urlString) throws IOException, WeatherServiceException {
        URI uri = URI.create(urlString);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        try {
            // Configurar a conexão usando valores do ConfigManager
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(configManager.getConnectionTimeout());
            connection.setReadTimeout(configManager.getReadTimeout());
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "WeatherApp/1.0");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(connection);
            } else {
                handleHttpError(responseCode, connection);
                return null; // Nunca executado, mas necessário para compilação
            }

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Lê a resposta da conexão HTTP
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    /**
     * Trata erros HTTP
     */
    private void handleHttpError(int responseCode, HttpURLConnection connection) throws WeatherServiceException {
        String errorMessage = "Erro HTTP " + responseCode;

        try {
            // Tenta ler a mensagem de erro da API
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();

            if (errorResponse.length() > 0) {
                errorMessage += ": " + errorResponse.toString();
            }
        } catch (Exception e) {
            // Ignora erros ao ler a mensagem de erro
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new WeatherServiceException("API Key inválida ou expirada");
            case HttpURLConnection.HTTP_NOT_FOUND:
                throw new WeatherServiceException("Cidade não encontrada");
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new WeatherServiceException("Requisição inválida - verifique o nome da cidade");
            case 429: // Too Many Requests
                throw new WeatherServiceException("Limite de requisições excedido - tente novamente mais tarde");
            default:
                throw new WeatherServiceException(errorMessage);
        }
    }

    /**
     * Converte a resposta JSON em objeto WeatherData
     */
    private WeatherData parseWeatherData(String jsonResponse, String cidade) throws WeatherServiceException {
        try {
            SimpleJsonParser parser = new SimpleJsonParser(jsonResponse);

            WeatherData weatherData = new WeatherData();
            weatherData.setCidade(cidade);

            // Buscar dados atuais (currentConditions)
            if (parser.hasKey("currentConditions")) {
                weatherData.setTemperaturaAtual(parser.getDouble("currentConditions.temp", 0.0));
                weatherData.setHumidade(parser.getDouble("currentConditions.humidity", 0.0));
                weatherData.setCondicaoTempo(parser.getString("currentConditions.conditions", "N/A"));
                weatherData.setPrecipitacao(parser.getDouble("currentConditions.precip", 0.0));
                weatherData.setVelocidadeVento(parser.getDouble("currentConditions.windspeed", 0.0));
                weatherData.setDirecaoVento(parser.getDouble("currentConditions.winddir", 0.0));
            }

            // Buscar dados do dia (days[0])
            if (parser.hasKey("days") && parser.getArraySize("days") > 0) {
                weatherData
                        .setTemperaturaMaxima(parser.getDouble("days[0].tempmax", weatherData.getTemperaturaAtual()));
                weatherData
                        .setTemperaturaMinima(parser.getDouble("days[0].tempmin", weatherData.getTemperaturaAtual()));

                // Se não tem dados atuais, usar dados do dia
                if (weatherData.getTemperaturaAtual() == 0.0) {
                    weatherData.setTemperaturaAtual(parser.getDouble("days[0].temp", 0.0));
                }
                if (weatherData.getHumidade() == 0.0) {
                    weatherData.setHumidade(parser.getDouble("days[0].humidity", 0.0));
                }
                if (weatherData.getCondicaoTempo().equals("N/A")) {
                    weatherData.setCondicaoTempo(parser.getString("days[0].conditions", "N/A"));
                }
                if (weatherData.getPrecipitacao() == 0.0) {
                    weatherData.setPrecipitacao(parser.getDouble("days[0].precip", 0.0));
                }
                if (weatherData.getVelocidadeVento() == 0.0) {
                    weatherData.setVelocidadeVento(parser.getDouble("days[0].windspeed", 0.0));
                }
                if (weatherData.getDirecaoVento() == 0.0) {
                    weatherData.setDirecaoVento(parser.getDouble("days[0].winddir", 0.0));
                }
            }

            // Validar dados
            if (!weatherData.isValid()) {
                throw new WeatherServiceException("Dados meteorológicos inválidos recebidos da API");
            }

            LOGGER.info("Dados meteorológicos processados com sucesso para: " + cidade);
            return weatherData;

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao processar resposta JSON", e);
            throw new WeatherServiceException("Erro ao processar dados meteorológicos: " + e.getMessage(), e);
        }
    }
}
