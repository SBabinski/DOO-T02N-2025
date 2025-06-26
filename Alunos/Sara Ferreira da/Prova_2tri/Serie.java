package com.faculdade.tvseries;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import com.google.gson.annotations.SerializedName; 

public class Serie {
    

    @SerializedName("name") 
    private String nome; 
    private String language;
    private List<String> genres;
    private String status;
    private String premiered;
    private String ended;
    private Rating rating;
    private Network network;
    private WebChannel webChannel;



    public Serie() {}

    public String getNome() {
        return nome;
    }

    public String getIdioma() {
        return language != null ? language : "Desconhecido";
    }

    public List<String> getGeneros() {
        return genres;
    }

    public String getStatus() {
        if (status == null) return "Desconhecido";

        return switch (status.toLowerCase()) {
            case "ended" -> "Concluída";
            case "running" -> "Em exibição";
            case "canceled" -> "Cancelada";
            case "to be determined" -> "A definir";
            default -> status;
        };
    }

    public String getDataEstreia() {
        return formatarData(premiered);
    }

    public String getDataEstreiaStr() {
        return premiered;
    }

    public String getDataTermino() {
        return ended != null ? formatarData(ended) : "Ainda em exibição";
    }

    public double getNota() {
        return (rating != null && rating.average != null) ? rating.average : 0.0;
    }

    public String getEmissora() {
        if (network != null) return network.name;
        if (webChannel != null) return webChannel.name;
        return "Desconhecida";
    }

    private String formatarData(String dataIso) {
        try {
            LocalDate data = LocalDate.parse(dataIso);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return data.format(formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            return "Data inválida";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Serie outra = (Serie) obj;
        return this.nome != null && outra.nome != null && this.nome.equalsIgnoreCase(outra.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome != null ? nome.toLowerCase() : null);
    }

    private static class Rating {
        Double average;
    }

    private static class Network {
        String name;
    }

    private static class WebChannel {
        String name;
    }
}