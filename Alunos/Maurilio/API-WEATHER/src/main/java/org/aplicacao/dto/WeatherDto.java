package org.aplicacao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    private String resolvedAddress;
    private String description;
    private List<String> datetimeList = new ArrayList<>();
    private List<Double> tempmaxList = new ArrayList<>();
    private List<Double> tempminList = new ArrayList<>();
    private List<Double> tempList = new ArrayList<>();
    private List<Double> humidityList = new ArrayList<>();
    private List<String> conditionsList = new ArrayList<>();
    private List<Double> precipList = new ArrayList<>();
    private List<Double> windspeedList = new ArrayList<>();
    private List<Double> winddirList = new ArrayList<>();

    @Override
    public String toString() {
        return "WeatherDto{" +
                "resolvedAddress='" + resolvedAddress + '\'' +
                ", description='" + description + '\'' +
                ", datetimeList=" + datetimeList +
                ", tempmaxList=" + tempmaxList +
                ", tempminList=" + tempminList +
                ", tempList=" + tempList +
                ", humidityList=" + humidityList +
                ", conditionsList=" + conditionsList +
                ", precipList=" + precipList +
                ", windspeedList=" + windspeedList +
                ", winddirList=" + winddirList +
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

    public List<String> getDatetimeList() {
        return datetimeList;
    }

    public void setDatetimeList(List<String> datetimeList) {
        this.datetimeList = datetimeList;
    }

    public List<Double> getTempmaxList() {
        return tempmaxList;
    }

    public void setTempmaxList(List<Double> tempmaxList) {
        this.tempmaxList = tempmaxList;
    }

    public List<Double> getTempminList() {
        return tempminList;
    }

    public void setTempminList(List<Double> tempminList) {
        this.tempminList = tempminList;
    }

    public List<Double> getTempList() {
        return tempList;
    }

    public void setTempList(List<Double> tempList) {
        this.tempList = tempList;
    }

    public List<Double> getHumidityList() {
        return humidityList;
    }

    public void setHumidityList(List<Double> humidityList) {
        this.humidityList = humidityList;
    }

    public List<String> getConditionsList() {
        return conditionsList;
    }

    public void setConditionsList(List<String> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public List<Double> getPrecipList() {
        return precipList;
    }

    public void setPrecipList(List<Double> precipList) {
        this.precipList = precipList;
    }

    public List<Double> getWindspeedList() {
        return windspeedList;
    }

    public void setWindspeedList(List<Double> windspeedList) {
        this.windspeedList = windspeedList;
    }

    public List<Double> getWinddirList() {
        return winddirList;
    }

    public void setWinddirList(List<Double> winddirList) {
        this.winddirList = winddirList;
    }

    @JsonProperty("days")
    private void unpackDays (Object value) {
        if (value instanceof List<?> list){
            for (Object item : list){
                if (item instanceof Map<?,?> map){
                    Object datetime = map.get("datetime");
                    Object tempmax = map.get("tempmax");
                    Object tempmin = map.get("tempmin");
                    Object temp = map.get("temp");
                    Object humidity = map.get("humidity");
                    Object conditions = map.get("conditions");
                    Object precip = map.get("precip");
                    Object windspeed = map.get("windspeed");
                    Object winddir = map.get("winddir");

                    datetimeList.add(datetime != null ? datetime.toString() : "");
                    tempmaxList.add(tempmax != null ? Double.valueOf(tempmax.toString()) : null);
                    tempminList.add(tempmin != null ? Double.valueOf(tempmin.toString()) : null);
                    tempList.add(temp != null ? Double.valueOf(temp.toString()) : null);
                    humidityList.add(humidity != null ? Double.valueOf(humidity.toString()) : null);
                    conditionsList.add(conditions != null ? String.valueOf(conditions.toString()) : null);
                    precipList.add(precip != null ? Double.valueOf(precip.toString()) : null);
                    windspeedList.add(windspeed != null ? Double.valueOf(windspeed.toString()) : null);
                    winddirList.add(winddir != null ? Double.valueOf(winddir.toString()) : null);
                }
            }
        }
    }

    public void exibirDadosWeather() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ 📍 LOCALIZAÇÃO: " + resolvedAddress);
        System.out.println("║ 📝 DESCRIÇÃO:   " + description);
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");

        for (int i = 0; i < datetimeList.size(); i++) {
            System.out.println("📅 Data de hoje:        " + datetimeList.get(i));
            System.out.println("🌡️ Temperatura Máxima:  " + tempmaxList.get(i) + " ºC");
            System.out.println("🌡️ Temperatura Mínima:  " + tempminList.get(i) + " ºC");
            System.out.println("🌡️ Temperatura Atual:   " + tempList.get(i) + " ºC");
            System.out.println(" 💧 Umidade:             " + humidityList.get(i) + " %");
            System.out.println("☁️ Condição:            " + conditionsList.get(i));
            System.out.println("🌧️ Precipitação:        " + precipList.get(i) + " mm");
            System.out.println("💨 Vento:               " + windspeedList.get(i) + " km/h");
            System.out.println("💨 Direção do Vento:    " + winddirList.get(i) + " °");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();
        }
    }
}
