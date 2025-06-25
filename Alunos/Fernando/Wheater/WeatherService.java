package Wheater;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherService {
    
    private final AuthService authService;
    private final ObjectMapper objectMapper;
    
    public WeatherService() {
        this.authService = new AuthService();
        this.objectMapper = new ObjectMapper();
    }
    
    public WeatherData getWeatherData(String city) throws WeatherApiException {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        authService.validateApiKey(scanner);
        
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String url = buildUrl(encodedCity);
            
            System.out.println("Fazendo requisição para: " + maskApiKey(url));
            
            String jsonResponse = HttpUtil.executeGetRequest(url);
            return objectMapper.readValue(jsonResponse, WeatherData.class);
            
        } catch (IOException e) {
            throw new WeatherApiException("Erro ao buscar dados meteorológicos para a cidade: " + city, e);
        }
    }
    
    private String buildUrl(String city) {
        return String.format("%s/%s?unitGroup=%s&include=%s&key=%s&contentType=json",
                ApiConfig.BASE_URL,
                city,
                ApiConfig.UNIT_GROUP,
                ApiConfig.INCLUDE,
                authService.getApiKey());
    }
    
    private String maskApiKey(String url) {
        if (url.contains("key=")) {
            return url.replaceAll("key=[^&]+", "key=***");
        }
        return url;
    }
    
    public void displayWeatherInfo(WeatherData weatherData) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           INFORMAÇÕES METEOROLÓGICAS");
        System.out.println("=".repeat(60));
        System.out.println("Localização: " + weatherData.getAddress());
        System.out.println("=".repeat(60));
        
        // Condições atuais
        if (weatherData.getCurrentConditions() != null) {
            var current = weatherData.getCurrentConditions();
            System.out.println("\nCONDIÇÕES ATUAIS");
            System.out.println("-".repeat(30));
            System.out.printf("Temperatura atual: %.1f°C%n", current.getTemperature());
            System.out.printf("Umidade: %.0f%%%n", current.getHumidity());
            System.out.println("Condições: " + translateConditions(current.getConditions()));
            
            if (current.getPrecipitation() > 0) {
                System.out.printf("Precipitação: %.1f mm%n", current.getPrecipitation());
            } else {
                System.out.println("Precipitação: Sem chuva");
            }
            
            System.out.printf("Vento: %.1f km/h %s (%.0f°)%n", 
                current.getWindSpeed(), 
                getWindDirection(current.getWindDirection()),
                current.getWindDirection());
        }
        
        // Dados do dia
        if (weatherData.getDays() != null && !weatherData.getDays().isEmpty()) {
            var today = weatherData.getDays().get(0);
            System.out.println("\nPREVISÃO PARA HOJE");
            System.out.println("-".repeat(30));
            System.out.printf("Máxima: %.1f°C%n", today.getMaxTemperature());
            System.out.printf("Mínima: %.1f°C%n", today.getMinTemperature());
            System.out.printf("Umidade média: %.0f%%%n", today.getHumidity());
            System.out.println("Condições: " + translateConditions(today.getConditions()));
            
            if (today.getPrecipitation() > 0) {
                System.out.printf("Precipitação: %.1f mm%n", today.getPrecipitation());
            } else {
                System.out.println("Precipitação: Sem previsão de chuva");
            }
            
            System.out.printf("Vento: %.1f km/h %s%n", 
                today.getWindSpeed(), 
                getWindDirection(today.getWindDirection()));
        }
        
        System.out.println("\n" + "=".repeat(60));
    }
    
    private String getWindDirection(double degrees) {
        String[] directions = {
            "Norte", "Norte-Nordeste", "Nordeste", "Leste-Nordeste",
            "Leste", "Leste-Sudeste", "Sudeste", "Sul-Sudeste",
            "Sul", "Sul-Sudoeste", "Sudoeste", "Oeste-Sudoeste",
            "Oeste", "Oeste-Noroeste", "Noroeste", "Norte-Noroeste"
        };
        
        int index = (int) Math.round(degrees / 22.5) % 16;
        return directions[index];
    }
    
    private String translateConditions(String conditions) {
        if (conditions == null) return "Não informado";
        
        String lower = conditions.toLowerCase();
        
        if (lower.contains("clear")) return "Céu limpo";
        if (lower.contains("sunny")) return "Ensolarado";
        if (lower.contains("partly cloudy")) return "Parcialmente nublado";
        if (lower.contains("cloudy")) return "Nublado";
        if (lower.contains("overcast")) return "Encoberto";
        if (lower.contains("rain")) return "Chuva";
        if (lower.contains("drizzle")) return "Garoa";
        if (lower.contains("thunderstorm")) return "Tempestade";
        if (lower.contains("snow")) return "Neve";
        if (lower.contains("fog")) return "Neblina";
        if (lower.contains("mist")) return "Névoa";
        
        return conditions;
    }
}