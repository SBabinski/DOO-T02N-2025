package org.aplicacao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    private String resolvedAddress;
    private String description;

    @JsonProperty("days")
    private List<DiaClimaDto> dias;

    @Override
    public String toString() {
        return "WeatherDto{" +
                "resolvedAddress='" + resolvedAddress + '\'' +
                ", description='" + description +
                '}';
    }

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public void setResolvedAddress(String resolvedAddress) {
        this.resolvedAddress = resolvedAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void exibirDadosWeather() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ 📍 LOCALIZAÇÃO: " + resolvedAddress);
        System.out.println("║ 📝 DESCRIÇÃO:   " + description);
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");

        for (DiaClimaDto dia : dias) {
            System.out.println("📅 Data:        " + dia.getDatetime());
            System.out.println("🌡️ Temperatura Máxima:  " + dia.getTempmax() + " ºC");
            System.out.println("🌡️ Temperatura Mínima:  " + dia.getTempmin() + " ºC");
            System.out.println("🌡️ Temperatura Atual:   " + dia.getTemp() + " ºC");
            System.out.println(" 💧 Umidade:             " + dia.getHumidity() + " %");
            System.out.println("☁️ Condição:            " + dia.getConditions());
            System.out.println("🌧️ Precipitação:        " + dia.getPrecip() + " mm");
            System.out.println("💨 Vento:               " + dia.getWindspeed() + " km/h");
            System.out.println("💨 Direção do Vento:    " + dia.getWinddir() + " °");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();
        }
    }
}
