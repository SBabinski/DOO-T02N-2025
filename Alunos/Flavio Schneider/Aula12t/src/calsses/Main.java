package calsses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class Main {

	public static void main(String[] args) {
	
		        Scanner scanner = new Scanner(System.in);
		        System.out.print("Digite o nome da cidade: ");
		        String cidade = scanner.nextLine();

		        String apiKey = "BAYU2G6VW2KRMUBFDG2ZHQ8B4"; 
		        String urlString = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" 
		                + cidade + "?unitGroup=metric&key=" + apiKey + "&include=current";

		        try {
		            
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
		            JsonObject atual = json.getAsJsonObject("currentConditions");

		            double temperatura = atual.get("temp").getAsDouble();
		            String condicao = atual.get("conditions").getAsString();
		            int umidade = atual.get("humidity").getAsInt();
		            double vento = atual.get("windspeed").getAsDouble();

		            JsonObject hoje = json.getAsJsonArray("days").get(0).getAsJsonObject();
		            double tempMax = hoje.get("tempmax").getAsDouble();
		            double tempMin = hoje.get("tempmin").getAsDouble();

		        
		            System.out.println("\n--- Clima em " + cidade + " ---");
		            System.out.println("Temperatura atual: " + temperatura + "°C");
		            System.out.println("Condição: " + condicao);
		            System.out.println("Umidade: " + umidade + "%");
		            System.out.println("Temp. máxima do dia: " + tempMax + "°C");
		            System.out.println("Temp. mínima do dia: " + tempMin + "°C");
		            System.out.println("Vento: " + vento + " km/h");

		        } catch (Exception e) {
		            System.out.println("Erro ao buscar clima: " + e.getMessage());
		        }
		    }
		

	}

