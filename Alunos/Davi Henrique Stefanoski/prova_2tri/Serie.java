package seriestv.pack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Serie {
    public String name;
    public String language;
    public List<String> genres;
    public Rating rating;
    public String status;
    public String premiered;
    public String ended;
    public Network network;

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Rating getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getPremiered() {
        return premiered;
    }

    public String getEnded() {
        return ended;
    }

    public Network getNetwork() {
        return network;
    }

    public Double getNota() {
        return (rating != null && rating.getAverage() != null) ? rating.getAverage() : null;
    }

    public int getOrdemStatus() {
        if (status == null) return 4;

        switch (status) {
            case "Running":
                return 1;
            case "Ended":
                return 2;
            case "Canceled":
                return 3;
            default:
                return 4;
        }
    }

    public LocalDate getDataEstreia() {
        if (premiered != null && !premiered.isEmpty()) {
            try {
                return LocalDate.parse(premiered);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void mostrarDetalhes() {
        System.out.println("Nome: " + getName());
        System.out.println("Idioma: " + getLanguage());
        System.out.println("Gêneros: " + String.join(", ", getGenres()));

        String nota = (getNota() != null) ? String.valueOf(getNota()) : "Sem nota";
        System.out.println("Nota: " + nota);

        String statusFormatado = switch (getStatus()) {
            case "Running" -> "Em exibição";
            case "Ended" -> "Finalizada";
            case "Canceled" -> "Cancelada";
            default -> "Desconhecido";
        };
        System.out.println("Status: " + statusFormatado);

        DateTimeFormatter entrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter saida = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String estreiaFormatada = "Desconhecida";
        String fimFormatado = "Ainda em exibição";

        try {
            if (getPremiered() != null && !getPremiered().isEmpty()) {
                LocalDate dataEstreia = LocalDate.parse(getPremiered(), entrada);
                estreiaFormatada = dataEstreia.format(saida);
            }
            if (getEnded() != null && !getEnded().isEmpty()) {
                LocalDate dataFim = LocalDate.parse(getEnded(), entrada);
                fimFormatado = dataFim.format(saida);
            }
        } catch (Exception e) {
            estreiaFormatada = "Erro ao formatar data";
        }

        System.out.println("Estreia: " + estreiaFormatada);
        if ("Ended".equals(getStatus())) {
            System.out.println("Fim: " + fimFormatado);
        }

        System.out.println("Emissora: " + (getNetwork() != null ? getNetwork().getName() : "Desconhecida"));
    }

    public List<String> infoResumida() {
        String ano = (getPremiered() != null && !getPremiered().isEmpty())
                ? getPremiered().substring(0, 4)
                : null;
        String emissora = (getNetwork() != null) ? getNetwork().getName() : null;
        String idioma = getLanguage();
        String nota = (getNota() != null) ? String.valueOf(getNota()) : null;

        List<String> detalhes = new ArrayList<>();
        if (ano != null) detalhes.add(ano);
        if (emissora != null) detalhes.add(emissora);
        if (idioma != null) detalhes.add(idioma);
        if (nota != null) detalhes.add(nota);

        return detalhes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Serie other = (Serie) obj;

        return name != null && name.equals(other.name) &&
               premiered != null && premiered.equals(other.premiered);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (premiered != null ? premiered.hashCode() : 0);
        return result;
    }
}
