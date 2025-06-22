package com.doo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private List<CityInformation> days;

    public List<CityInformation> getDays() {
        return days;
    }

    public void setDays(List<CityInformation> days) {
        this.days = days;
    }
}
