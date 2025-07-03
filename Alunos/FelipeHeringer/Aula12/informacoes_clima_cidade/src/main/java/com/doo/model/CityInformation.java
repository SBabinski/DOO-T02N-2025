package com.doo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CityInformation {
    private Float temp;
    private Float tempmin;
    private Float tempmax;
    private Float humidity;
    private String conditions;
    private Float precip;
    private Float windspeed;
    private Float winddir;

    public Float getTemp() {
        return temp;
    }
    public void setTemp(Float temp) {
        this.temp = temp;
    }
    public Float getTempmin() {
        return tempmin;
    }
    public void setTempmin(Float tempmin) {
        this.tempmin = tempmin;
    }
    public Float getTempmax() {
        return tempmax;
    }
    public void setTempmax(Float tempmax) {
        this.tempmax = tempmax;
    }
    public Float getHumidity() {
        return humidity;
    }
    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    public Float getPrecip() {
        return precip;
    }
    public void setPrecip(Float precip) {
        this.precip = precip;
    }
    public Float getWindspeed() {
        return windspeed;
    }
    public void setWindspeed(Float windspeed) {
        this.windspeed = windspeed;
    }
    public Float getWinddir() {
        return winddir;
    }
    public void setWinddir(Float winddir) {
        this.winddir = winddir;
    }
    
    
}
