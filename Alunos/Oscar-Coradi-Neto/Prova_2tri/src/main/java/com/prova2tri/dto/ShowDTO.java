package com.prova2tri.dto;

import java.util.List;

public class ShowDTO {
    public int id;
    public String name;
    public String language;
    public List<String> genres;
    public RatingDTO rating;
    public String status;
    public String premiered; 
    public String ended;     
    public NetworkDTO network;

    
    public static class RatingDTO {
        public Double average;
    }

    public static class NetworkDTO {
        public String name;
        public CountryDTO country;
    }

    public static class CountryDTO {
        public String name;
    }
}