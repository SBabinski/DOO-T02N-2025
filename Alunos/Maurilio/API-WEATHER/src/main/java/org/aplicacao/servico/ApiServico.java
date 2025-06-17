package org.aplicacao.servico;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aplicacao.dto.WeatherDto;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiServico {

    WeatherDto weatherDto = new WeatherDto();
    ObjectMapper mapper = new ObjectMapper();

    public WeatherDto getLocation(String cep) throws IOException, InterruptedException {
        try {
            String cepCodificado = URLEncoder.encode(cep, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + cepCodificado +"/today/?unitGroup=metric&key=E7NACVAB6YJ3P3DXYTM8PUCSQ&contentType=json"))
                    .method("GET", HttpRequest.BodyPublishers.noBody()).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() != 200){
                System.out.println();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║                 ❌ ERRO                 ║");
                System.out.println("╠════════════════════════════════════════╣");
                System.out.println("║         INSIRA O CEP CORRETO..         ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.println();
            } else {
                weatherDto = mapper.readValue(response.body(), WeatherDto.class);
                weatherDto.exibirDadosWeather();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return weatherDto;
    }
}
