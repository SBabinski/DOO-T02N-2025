package com.fag.doo_series.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

public class Series {
    private Integer id;
    private String name;
    private String language;
    private ArrayList<String> genres;
    private float ratingAvarage;
    private String state;
    private Date premieredDate;
    private Date endedDate = null;
    private String broadcastingStationName;
    private String imageLink;

    public Series() {

    }

    public Series(Document doc) {
        this.id = doc.getInteger("series_id");
        this.name = doc.getString("series_name");
        this.language = doc.getString("series_language");
        this.genres = new ArrayList<>(doc.getList("series_genres", String.class));
        this.ratingAvarage = doc.getDouble("series_rating").floatValue();
        this.state = doc.getString("series_state");
        this.broadcastingStationName = doc.getString("series_broadcasting_station_name");

        String premieredDateStr = doc.getString("series_premiered_date");
        String endedDateStr = doc.getString("series_ended_date");

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.premieredDate = dateFormat.parse(premieredDateStr);
            if (endedDateStr != null) {
                this.endedDate = dateFormat.parse(endedDateStr);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format", e);
        }
        
        this.imageLink = doc.getString("series_image_url");
    }

    public Map<String, Object> toJson() {
        Map<String, Object> seriesJson = new HashMap<>();
        seriesJson.put("series_id", id);
        seriesJson.put("series_name", name);
        seriesJson.put("series_language", language);
        if (genres == null) {
            this.genres = new ArrayList<>();
        }
        seriesJson.put("series_genres", genres);
        seriesJson.put("series_rating", ratingAvarage);
        seriesJson.put("series_state", state);

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String premieredDateString = formatter.format((Date) premieredDate);
            seriesJson.put("series_premiered_date", premieredDateString);

            if (endedDate != null) {
                String endedDateString = formatter.format((Date) endedDate);
                seriesJson.put("series_ended_date", endedDateString);
            } else {
                seriesJson.put("series_ended_date", null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        seriesJson.put("series_broadcasting_station_name", broadcastingStationName);
        seriesJson.put("series_image_url", imageLink);

        return seriesJson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public float getRatingAvarage() {
        return ratingAvarage;
    }

    public void setRatingAvarage(float ratingAvarage) {
        this.ratingAvarage = ratingAvarage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getPremieredDate() {
        return premieredDate;
    }

    public void setPremieredDate(Date premieredDate) {
        this.premieredDate = premieredDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public String getBroadcastingStationName() {
        return broadcastingStationName;
    }

    public void setBroadcastingStationName(String broadcastingStationName) {
        this.broadcastingStationName = broadcastingStationName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getStringPremieredDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String premieredString = formatter.format((Date) premieredDate);
        return premieredString;
    }
    
    public String getStringEndedDate() {
        if (endedDate != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String endedString = formatter.format((Date) endedDate);
            return endedString;
        }
        return null;
    }
}
