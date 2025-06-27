import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlBuilder {

    private static final String API_ENDPOINT = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s?unitGroup=metric&include=days,current&key=%s&contentType=json&lang=pt";

    public static String build(String city, String apiKey) {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        return String.format(API_ENDPOINT, encodedCity, apiKey);
    }
}