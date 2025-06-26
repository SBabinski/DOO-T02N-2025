package services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequester {

    public static JsonObject getSingle(String query) {
        try {
            String urlStr = "https://api.tvmaze.com/singlesearch/shows?q=" + query;
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); 
            connection.setReadTimeout(5000);   
            connection.connect();

            int status = connection.getResponseCode();
            if (status == 200) {
                try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                    return JsonParser.parseReader(reader).getAsJsonObject();
                }
            } else {
                System.err.println("Erro na requisição HTTP: " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
