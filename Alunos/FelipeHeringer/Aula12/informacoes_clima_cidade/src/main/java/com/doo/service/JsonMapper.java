package com.doo.service;

import com.doo.model.CityInformation;
import com.doo.model.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private ObjectMapper objectMapper = new ObjectMapper();;

    public CityInformation mapJsonToCityInformation(String json) {
        try {
            CityInformation cityInformation = objectMapper.readValue(json, WeatherResponse.class).getDays().get(0);
            return cityInformation;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear JSON para CityInformation: " + e.getMessage(), e);
        }
    }
    
}
