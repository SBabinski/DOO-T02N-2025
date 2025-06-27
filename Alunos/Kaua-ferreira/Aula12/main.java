package com.meuaplicativoclima;

import java.util.Scanner;

import com.meuaplicativoclima.model.CurrentConditions;
import com.meuaplicativoclima.model.Day;
import com.meuaplicativoclima.model.WeatherData;
import com.meuaplicativoclima.service.VisualCrossingService;

public class main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VisualCrossingService weatherService = new VisualCrossingService();

        System.out.println("--- Aplicativo do Clima ---");
        System.out.print("Digite o nome da cidade para consultar o clima (Ex: Cascavel, PR): ");
        String city = scanner.nextLine();

        WeatherData data = weatherService.getWeatherData(city);

        if (data != null) {
            System.out.println("\n--- Condições Climáticas para " + data.getResolvedAddress() + " ---");

            CurrentConditions current = data.getCurrentConditions();
            if (current != null) {
                System.out.println("\n--- Condições Atuais ---");
                System.out.println("Temperatura: " + current.getTemp() + "°C");
                System.out.println("Umidade: " + current.getHumidity() + "%");
                System.out.println("Condição: " + current.getConditions());
                System.out.println("Precipitação (última hora): " + (current.getPrecip() != null ? current.getPrecip() : 0.0) + " mm");
                System.out.println("Vento: " + current.getWindspeed() + " km/h (Direção: " + getWindDirection(current.getWinddir()) + ")");
            } else {
                System.out.println("Não foi possível obter as condições atuais.");
            }

            if (data.getDays() != null && !data.getDays().isEmpty()) {
                Day today = data.getDays().get(0); // Pega o primeiro dia (hoje)
                System.out.println("\n--- Previsão para Hoje (" + today.getDatetime() + ") ---");
                System.out.println("Temperatura Máxima: " + today.getTempmax() + "°C");
                System.out.println("Temperatura Mínima: " + today.getTempmin() + "°C");
                System.out.println("Condição do Dia: " + today.getConditions());
                System.out.println("Precipitação Diária: " + (today.getPrecip() != null ? today.getPrecip() : 0.0) + " mm");
            } else {
                System.out.println("Não foi possível obter a previsão diária.");
            }

        } else {
            System.out.println("Não foi possível obter dados do clima para a cidade informada. Verifique o nome da cidade ou sua chave da API.");
        }

        scanner.close();
    }

    // Método auxiliar para converter direção do vento em graus para uma string
    private static String getWindDirection(Double degrees) {
        if (degrees == null) return "N/A";
        if (degrees >= 337.5 || degrees < 22.5) return "Norte";
        if (degrees >= 22.5 && degrees < 67.5) return "Nordeste";
        if (degrees >= 67.5 && degrees < 112.5) return "Leste";
        if (degrees >= 112.5 && degrees < 157.5) return "Sudeste";
        if (degrees >= 157.5 && degrees < 202.5) return "Sul";
        if (degrees >= 202.5 && degrees < 247.5) return "Sudoeste";
        if (degrees >= 247.5 && degrees < 292.5) return "Oeste";
        if (degrees >= 292.5 && degrees < 337.5) return "Noroeste";
        return "N/A";
    }
}
