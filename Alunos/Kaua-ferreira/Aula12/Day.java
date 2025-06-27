package com.meuaplicativoclima.model;

public class Day {
    private String datetime; // Ex: "2024-06-26"
    private Double tempmax;
    private Double tempmin;
    private Double humidity;
    private Double precip; // Quantidade de precipitação
    private String conditions; // Condição do tempo (ex: "Partially cloudy")
    private Double windspeed;
    private Double winddir; // Direção do vento em graus
    // Se precisar de tipo de precipitação: private List<String> precipType;

    // Getters e Setters
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
    public Double getTempmax() { return tempmax; }
    public void setTempmax(Double tempmax) { this.tempmax = tempmax; }
    public Double getTempmin() { return tempmin; }
    public void setTempmin(Double tempmin) { this.tempmin = tempmin; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Double getPrecip() { return precip; }
    public void setPrecip(Double precip) { this.precip = precip; }
    public String getConditions() { return conditions; }
    public void setConditions(String conditions) { this.conditions = conditions; }
    public Double getWindspeed() { return windspeed; }
    public void setWindspeed(Double windspeed) { this.windspeed = windspeed; }
    public Double getWinddir() { return winddir; }
    public void setWinddir(Double winddir) { this.winddir = winddir; }
}