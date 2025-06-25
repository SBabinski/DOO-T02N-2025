package Wheater;

public class ApiConfig {

    public static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
    public static final String API_KEY = System.getProperty("weather.api.key", "SUA_API_KEY_AQUI");
    public static final String UNIT_GROUP = "metric";
    public static final String INCLUDE = "current";

    private ApiConfig() {
        
    }
    
}