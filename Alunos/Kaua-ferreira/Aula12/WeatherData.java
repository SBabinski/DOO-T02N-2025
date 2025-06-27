package com.meuaplicativoclima.model;

import java.util.List;

public class WeatherData {
    private String resolvedAddress;
    private List<Day> days; 
    private CurrentConditions currentConditions;

 
    public String getResolvedAddress() { return resolvedAddress; }
    public void setResolvedAddress(String resolvedAddress) { this.resolvedAddress = resolvedAddress; }
    public List<Day> getDays() { return days; }
    public void setDays(List<Day> days) { this.days = days; }
    public CurrentConditions getCurrentConditions() { return currentConditions; }
    public void setCurrentConditions(CurrentConditions currentConditions) { this.currentConditions = currentConditions; }
}
