package org.climatempo;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AppClimaTempo {
    private static final String API_KEY = "KLLRVCVJDHSZY65KRCDKU6FU7";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();
        scanner.close();

        try {
            String urlString = String.format(
                    "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
                    cidade, API_KEY
            );

            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuilder resposta = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                resposta.append(inputLine);
            }
            in.close();

            JsonObject json = JsonParser.parseString(resposta.toString()).getAsJsonObject();
            JsonArray dias = json.getAsJsonArray("days");
            JsonObject hoje = dias.get(0).getAsJsonObject();

            double temperaturaAtual = hoje.get("temp").getAsDouble();
            double tempMax = hoje.get("tempmax").getAsDouble();
            double tempMin = hoje.get("tempmin").getAsDouble();
            double humidade = hoje.get("humidity").getAsDouble();
            double precip = hoje.get("precip").getAsDouble();
            double velocidadeVento = hoje.get("windspeed").getAsDouble();
            String direcaoVento = hoje.get("winddir").getAsString();
            String condicao = hoje.get("conditions").getAsString();

            System.out.println("\n----- CLIMA DE HOJE EM " + cidade.toUpperCase() + " -----");
            System.out.println("Temperatura atual: " + temperaturaAtual + "°C");
            System.out.println("Máxima: " + tempMax + "°C");
            System.out.println("Mínima: " + tempMin + "°C");
            System.out.println("Umidade: " + humidade + "%");
            System.out.println("Condição do tempo: " + condicao);
            System.out.println("Precipitação: " + precip + " mm");
            System.out.println("Vento: " + velocidadeVento + " km/h - Direção: " + direcaoVento + "°");

        } catch (Exception e) {
            System.out.println("Erro ao buscar os dados: " + e.getMessage());
        }
    }
}
