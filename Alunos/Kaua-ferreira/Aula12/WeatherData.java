package com.meuaplicativoclima.model;

import java.util.List;

public class WeatherData {
    private String resolvedAddress; // Ex: "Brasilia, DF, Brazil"
    private List<Day> days; // Lista de dias (para máximas/mínimas do dia)
    private CurrentConditions currentConditions; // Condições atuais

    // Getters e Setters
    public String getResolvedAddress() { return resolvedAddress; }
    public void setResolvedAddress(String resolvedAddress) { this.resolvedAddress = resolvedAddress; }
    public List<Day> getDays() { return days; }
    public void setDays(List<Day> days) { this.days = days; }
    public CurrentConditions getCurrentConditions() { return currentConditions; }
    public void setCurrentConditions(CurrentConditions currentConditions) { this.currentConditions = currentConditions; }
}