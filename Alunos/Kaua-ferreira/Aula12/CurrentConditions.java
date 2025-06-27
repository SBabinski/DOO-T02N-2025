package com.meuaplicativoclima.model;

public class CurrentConditions {
    private String datetime; 
    private Double temp; 
    private Double humidity;
    private Double precip;
    private Double windspeed;
    private Double winddir;
    private String conditions;

    
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
    public Double getTemp() { return temp; }
    public void setTemp(Double temp) { this.temp = temp; }
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    public Double getPrecip() { return precip; }
    public void setPrecip(Double precip) { this.precip = precip; }
    public Double getWindspeed() { return windspeed; }
    public void setWindspeed(Double windspeed) { this.windspeed = windspeed; }
    public Double getWinddir() { return winddir; }
    public void setWinddir(Double winddir) { this.winddir = winddir; }
    public String getConditions() { return conditions; }
    public void setConditions(String conditions) { this.conditions = conditions; }
}

