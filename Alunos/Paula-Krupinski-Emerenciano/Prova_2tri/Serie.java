import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Serie {
    private String name;
    private String language;
    private List<String> genres;
    private String status;
    private String premiered;
    private String ended;
    private Rating rating;
    private Network network;
    private WebChannel webChannel;


    public String getNome() {
        return name;
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
            case "cancelled" -> "Cancelada";
            default -> status;
        };
    }


    public String getDataEstreia() {
        return formatarData(premiered);
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
            LocalDate data = LocalDate.parse(dataIso); // assume formato ISO
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return data.format(formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            return "Data inválida";
        }
    }

    // Comparação - validado
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Serie)) return false;
        Serie outra = (Serie) obj;
        return this.name.equalsIgnoreCase(outra.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
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
