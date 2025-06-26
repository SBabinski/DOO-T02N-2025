package org.example.dto;

public class SerieDto {
    public String name;
    public String language;
    public String[] genres;
    public Rating rating;
    public String status;
    public String premiered;
    public String ended;
    public Network network;

    @Override
    public String toString() {
        String generosStr = "";
        if (genres != null && genres.length > 0) {
            for (int i = 0; i < genres.length; i++) {
                generosStr += genres[i];
                if (i < genres.length - 1) {
                    generosStr += ", ";
                }
            }
        } else {
            generosStr = "N/A";
        }

        String nota = "N/A";
        if (rating != null) {
            if (rating.average != null) {
                nota = rating.average.toString();
            }
        }

        String emissora = "N/A";
        if (network != null) {
            if (network.name != null) {
                emissora = network.name;
            }
        }

        String estreia;
        if (premiered == null) {
            estreia = "N/A";
        } else {
            estreia = premiered;
        }

        String termino;
        if (ended == null) {
            termino = "N/A";
        } else {
            termino = ended;
        }

        String estado;
        if (status == null) {
            estado = "N/A";
        } else {
            estado = status;
        }

        String idioma;
        if (language == null) {
            idioma = "N/A";
        } else {
            idioma = language;
        }

        String nomeS;
        if (name == null) {
            nomeS = "N/A";
        } else {
            nomeS = name;
        }

        String texto = "Nome: " + nomeS + "\n" +
                "Idioma: " + idioma + "\n" +
                "Gêneros: " + generosStr + "\n" +
                "Nota geral: " + nota + "\n" +
                "Status: " + estado + "\n" +
                "Data de estreia: " + estreia + "\n" +
                "Data de término: " + termino + "\n" +
                "Emissora: " + emissora;

        return texto;
    }

    public static class Rating {
        public Double average;
    }

    public static class Network {
        public String name;
    }
}
