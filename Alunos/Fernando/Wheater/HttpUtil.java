package Wheater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    
    private static final int TIMEOUT = 30000; // 30 segundos
    
    public static String executeGetRequest(String urlString) throws IOException {
        URL url = URL.of(URI.create(urlString), null);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Weather-App/1.0");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                StringBuilder errorResponse = new StringBuilder();
                try (BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                }
                throw new IOException("HTTP Error: " + responseCode + " - " + connection.getResponseMessage()
                        + "\nDetalhes: " + errorResponse.toString());
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }
            }

            return response.toString().trim();
        } finally {
            connection.disconnect();
        }
    }
}