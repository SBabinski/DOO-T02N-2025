package com.fag.doo_series.utils;

import com.fag.doo_series.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class JsonUserLoader {
    private static final String JSON_FILE_PATH = "Alunos/FelipeHeringer/Prova_2tri/doo_series/src/main/java/com/fag/doo_series/assets/data/test.json";
    private ObjectMapper objectMapper;

    public JsonUserLoader() {
        this.objectMapper = new ObjectMapper();
    }

    public Usuario loadUserFromJson() {
        try {
            File jsonFile = new File(JSON_FILE_PATH);
            if (!jsonFile.exists()) {
                System.out.println("JSON file not found: " + JSON_FILE_PATH);
                return null;
            }

            JsonNode rootNode = objectMapper.readTree(jsonFile);

            String userName = rootNode.get("user_name").asText();
            String userCpf = rootNode.get("user_cpf").asText();

            List<Object> favoriteGenres = new ArrayList<>();
            JsonNode genresNode = rootNode.get("user_favorite_genres");
            if (genresNode != null && genresNode.isArray()) {
                for (JsonNode genreNode : genresNode) {
                    favoriteGenres.add(genreNode.asText());
                }
            }

            Usuario user = new Usuario(userName, userCpf, favoriteGenres);

            JsonNode favoriteSeriesNode = rootNode.get("favorite_series");
            if (favoriteSeriesNode != null && favoriteSeriesNode.isArray()) {
                for (JsonNode seriesNode : favoriteSeriesNode) {
                    Series series = parseSeriesFromJson(seriesNode);
                    if (series != null) {
                        user.addSeriesOnfavoriteList(series);
                    }
                }
            }

            JsonNode watchLaterNode = rootNode.get("series_watch_later");
            if (watchLaterNode != null && watchLaterNode.isArray()) {
                for (JsonNode seriesNode : watchLaterNode) {
                    Series series = parseSeriesFromJson(seriesNode);
                    if (series != null) {
                        user.addSeriesOnWatchLaterList(series);
                    }
                }
            }

            JsonNode watchedNode = rootNode.get("series_watched");
            if (watchedNode != null && watchedNode.isArray()) {
                for (JsonNode seriesNode : watchedNode) {
                    Series series = parseSeriesFromJson(seriesNode);
                    if (series != null) {
                        user.addSeriesOnWatchedList(series);
                    }
                }
            }

            return user;

        } catch (IOException e) {
            System.err.println("Error loading user from JSON: " + e.getMessage());
            return null;
        }
    }

    public void saveUserToJson(Usuario user) {
        try {
            ObjectNode rootNode = objectMapper.createObjectNode();

            rootNode.put("user_name", user.getName());
            rootNode.put("user_cpf", user.getCpf());

            ArrayNode genresArray = rootNode.putArray("user_favorite_genres");
            for (Object genre : user.getFavoriteGenres()) {
                genresArray.add(genre.toString());
            }

            ArrayNode favoriteSeriesArray = rootNode.putArray("favorite_series");
            for (Series series : user.getFavoriteSeries()) {
                favoriteSeriesArray.add(convertSeriesToJson(series));
            }

            ArrayNode watchLaterArray = rootNode.putArray("series_watch_later");
            for (Series series : user.getSeriesWatchLater()) {
                watchLaterArray.add(convertSeriesToJson(series));
            }

            ArrayNode watchedArray = rootNode.putArray("series_watched");
            for (Series series : user.getSeriesWatched()) {
                watchedArray.add(convertSeriesToJson(series));
            }

            File jsonFile = new File(JSON_FILE_PATH);
            jsonFile.getParentFile().mkdirs();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);

        } catch (IOException e) {
            System.err.println("Error saving user to JSON: " + e.getMessage());
        }
    }

    private Series parseSeriesFromJson(JsonNode seriesNode) {
        Series series = new Series();

        try {
            int id = seriesNode.get("series_id").asInt();
            String name = seriesNode.get("series_name").asText();
            String language = seriesNode.get("series_language").asText();
            double rating = seriesNode.get("series_rating").asDouble();
            String state = seriesNode.get("series_state").asText();
            String imageUrl = seriesNode.get("series_image_url").asText();
            String broadcastingStation = seriesNode.get("series_broadcasting_station_name").asText();

            ArrayList<String> genres = new ArrayList<>();
            JsonNode genresNode = seriesNode.get("series_genres");
            if (genresNode != null && genresNode.isArray()) {
                for (JsonNode genreNode : genresNode) {
                    genres.add(genreNode.asText());
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date premieredDate = null;
            JsonNode premieredNode = seriesNode.get("series_premiered_date");
            if (premieredNode != null && !premieredNode.isNull()) {
                String premieredStr = premieredNode.asText();
                if (!premieredStr.isEmpty()) {
                    premieredDate = dateFormat.parse(premieredStr);
                }
            }

            Date endedDate = null;
            JsonNode endedNode = seriesNode.get("series_ended_date");
            if (endedNode != null && !endedNode.isNull()) {
                String endedStr = endedNode.asText();
                if (!endedStr.isEmpty()) {
                    endedDate = dateFormat.parse(endedStr);
                }
            }

            series.setId(id);
            series.setName(name);
            series.setLanguage(language);
            series.setGenres(genres);
            series.setRatingAvarage((float) rating);
            series.setState(state);
            series.setPremieredDate(premieredDate);
            series.setEndedDate(endedDate);
            series.setImageLink(imageUrl);

            series.setBroadcastingStationName(broadcastingStation);
            return series;

        } catch (Exception e) {
            System.err.println("Error parsing series from JSON: " + e.getMessage());
            return null;
        }
    }

    private ObjectNode convertSeriesToJson(Series series) {
        ObjectNode seriesNode = objectMapper.createObjectNode();

        seriesNode.put("series_id", series.getId());
        seriesNode.put("series_name", series.getName());
        seriesNode.put("series_language", series.getLanguage());
        seriesNode.put("series_rating", series.getRatingAvarage());
        seriesNode.put("series_state", series.getState());
        seriesNode.put("series_image_url", series.getImageLink());
        seriesNode.put("series_broadcasting_station_name", series.getBroadcastingStationName());

        ArrayNode genresArray = seriesNode.putArray("series_genres");
        for (String genre : series.getGenres()) {
            genresArray.add(genre);
        }

        if (series.getPremieredDate() != null) {
            seriesNode.put("series_premiered_date", series.getStringPremieredDate());
        } else {
            seriesNode.putNull("series_premiered_date");
        }

        if (series.getEndedDate() != null) {
            seriesNode.put("series_ended_date", series.getStringEndedDate());
        } else {
            seriesNode.putNull("series_ended_date");
        }

        return seriesNode;
    }
}