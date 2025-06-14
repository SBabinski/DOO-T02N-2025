package com.seuprojeto.dto;

import java.util.List;

// DTO para mapear a estrutura do objeto "show" dentro da resposta da API
public class ShowDTO {
    public int id;
    public String name;
    public String language;
    public List<String> genres;
    public RatingDTO rating;
    public String status;
    public String premiered; // Data de estreia como String
    public String ended;     // Data de término como String
    public NetworkDTO network;

    // Classes aninhadas para mapear sub-objetos do JSON
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