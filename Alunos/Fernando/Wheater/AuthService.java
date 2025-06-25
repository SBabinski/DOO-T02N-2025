package Wheater;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class AuthService {

    private static String apiKey = null;

    public static void validateApiKey(Scanner scanner) {
        while (true) {
            if (apiKey == null || apiKey.trim().isEmpty()) {
                apiKey = System.getProperty("weather.api.key");

                if (apiKey == null || apiKey.trim().isEmpty()) {
                    apiKey = ApiConfig.API_KEY;
                }

                if (apiKey == null || apiKey.trim().isEmpty() || "SUA_API_KEY_AQUI".equals(apiKey)) {
                    System.out.print("Digite sua API Key: ");
                    apiKey = scanner.nextLine().trim();
                }
            }

            if (isValidKey()) {
                break;
            } else {
                System.out.println("API Key inválida. Tente novamente.");
                apiKey = null;
            }
        }
    }

    public static String getApiKey() {
        return apiKey;
    }

    private static boolean isValidKey() {
        try {
            String testUrl = String.format(
                "%s/cascavel?unitGroup=%s&include=%s&key=%s&contentType=json",
                ApiConfig.BASE_URL,
                ApiConfig.UNIT_GROUP,
                ApiConfig.INCLUDE,
                apiKey
            );

            URL url = URL.of(URI.create(testUrl), null);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            return false;
        }
    }
}
