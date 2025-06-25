package com.cinelume.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Serie {
    private final String nome;
    private final String idioma;
    private final List<String> generos;
    private final double nota;
    private final String status;
    private final LocalDate dataEstreia;
    private final String emissora;

    public Serie(String nome, String idioma, List<String> generos, double nota,
                String status, String dataEstreia, String emissora) {
        this.nome = nome;
        this.idioma = idioma;
        this.generos = generos;
        this.nota = nota;
        this.status = status;
        this.dataEstreia = parseDate(dataEstreia);
        this.emissora = emissora;
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.equalsIgnoreCase("N/A")) return null;
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            System.err.println("Erro ao parsear data: " + date);
            return null;
        }
    }

    public static Serie fromJson(JsonObject json) {
        List<String> generos = new ArrayList<>();
        if (json.has("genres") && json.get("genres").isJsonArray()) {
            JsonArray generosJson = json.getAsJsonArray("genres");
            generosJson.forEach(e -> {
                if (!e.isJsonNull()) generos.add(e.getAsString());
            });
        }

        double nota = 0.0;
        if (json.has("rating") && json.get("rating").isJsonObject()) {
            try {
                nota = json.getAsJsonObject("rating").get("average").getAsDouble();
            } catch (Exception e) {
                System.err.println("Erro ao ler nota: " + e.getMessage());
            }
        }

        String dataEstreia = null;
        if (json.has("premiered") && !json.get("premiered").isJsonNull()) {
            dataEstreia = json.get("premiered").getAsString();
        }

        String emissora = "N/A";
        if (json.has("network") && json.get("network").isJsonObject()) {
            try {
                emissora = json.getAsJsonObject("network").get("name").getAsString();
            } catch (Exception e) {
                System.err.println("Erro ao ler emissora: " + e.getMessage());
            }
        }

        return new Serie(
            json.get("name").getAsString(),
            json.get("language").getAsString(),
            generos,
            nota,
            json.get("status").getAsString(),
            dataEstreia,
            emissora
        );
    }

    // Getters
    public String getNome() { return nome; }
    public String getIdioma() { return idioma; }
    public List<String> getGeneros() { return generos; }
    public double getNota() { return nota; }
    public String getStatus() { return status; }
    public LocalDate getDataEstreia() { return dataEstreia; }
    public String getEmissora() { return emissora; }

    public String getDataEstreiaFormatada() {
        return dataEstreia != null ? 
            dataEstreia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
    }

    @Override
    public String toString() {
        return String.format(
            """
            Nome: %s
            Idioma: %s
            Gêneros: %s
            Nota: %.1f
            Status: %s
            Estreia: %s
            Emissora: %s
            """,
            nome, idioma, String.join(", ", generos), nota, status,
            getDataEstreiaFormatada(), emissora
        );
    }
}