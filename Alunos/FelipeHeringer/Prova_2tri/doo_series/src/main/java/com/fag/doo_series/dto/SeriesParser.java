package com.fag.doo_series.dto;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import com.fag.doo_series.model.Series;

public class SeriesParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Series> parseSeriesList(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return Collections.emptyList();
        }

        try (StringReader reader = new StringReader(jsonResponse);
                JsonReader jsonReader = Json.createReader(reader)) {

            JsonArray jsonArray = jsonReader.readArray();
            List<Series> seriesList = new ArrayList<>();

            jsonArray.forEach(jsonValue -> {
                if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT) {
                    JsonObject seriesData = jsonValue.asJsonObject();
                    JsonObject showData = seriesData.getJsonObject("show");

                    if (showData != null) {
                        Series series = parseSeries(showData);
                        seriesList.add(series);
                    }
                }

            });

            return seriesList;
        } catch (Exception e) {
            System.err.println("Erro parse json: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private Series parseSeries(JsonObject showData) {
        Series series = new Series();
        series.setId(getIntValue(showData, "id"));
        series.setName(getStringValue(showData, "name"));
        series.setLanguage(getStringValue(showData, "language"));

        JsonArray genresArray = showData.getJsonArray("genres");
        if (genresArray != null) {
            ArrayList<String> genres = new ArrayList<>();
            genresArray.forEach(genreValue -> {
                if (genreValue.getValueType() == JsonValue.ValueType.STRING) {
                    genres.add(genreValue.toString().replace("\"", ""));

                }
            });
            series.setGenres(genres);
        } else {
            series.setGenres(new ArrayList<>());
        }

        series.setState(getStringValue(showData, "status"));

        String premieredStr = getStringValue(showData, "premiered");
        if (premieredStr != null) {
            series.setPremieredDate(java.sql.Date.valueOf(LocalDate.parse(premieredStr, DATE_FORMATTER)));
        }

        String endedStr = getStringValue(showData, "ended");
        if (endedStr != null) {
            series.setEndedDate(java.sql.Date.valueOf(LocalDate.parse(endedStr, DATE_FORMATTER)));
        }

        JsonObject ratingObject = showData.getJsonObject("rating");
        if (ratingObject != null) {
            JsonValue averageValue = ratingObject.get("average");
            if (averageValue != null && averageValue.getValueType() != JsonValue.ValueType.NULL) {
                series.setRatingAvarage(Float.parseFloat(averageValue.toString()));
            }
        }

        if (showData.containsKey("network") &&
                showData.get("network") != null &&
                showData.get("network").getValueType() != JsonValue.ValueType.NULL) {
            JsonObject networkObject = showData.getJsonObject("network");
            {
                series.setBroadcastingStationName(getStringValue(networkObject, "name"));
            }

        } else if (showData.containsKey("webChannel") &&
                showData.get("webChannel") != null &&
                showData.get("webChannel").getValueType() != JsonValue.ValueType.NULL) {
            JsonObject webChannelObject = showData.getJsonObject("webChannel");
            series.setBroadcastingStationName(getStringValue(webChannelObject, "name"));
        }
        JsonObject imageObject = showData.getJsonObject("image");

        if(imageObject != null){
            series.setImageLink(getStringValue(imageObject, "original"));
        }
        return series;
    }

    private String getStringValue(JsonObject jsonObject, String key) {
        if (jsonObject.containsKey(key)) {
            JsonValue value = jsonObject.get(key);
            if (value != null && value.getValueType() != JsonValue.ValueType.NULL) {
                return value.toString().replace("\"", "");
            }
        }
        return null;
    }

    private Integer getIntValue(JsonObject jsonObject, String key) {
        if (jsonObject.containsKey(key)) {
            JsonValue value = jsonObject.get(key);
            if (value != null && value.getValueType() != JsonValue.ValueType.NULL) {
                return Integer.parseInt(value.toString().replace("\"", ""));
            }
        }
        return null;
    }

}