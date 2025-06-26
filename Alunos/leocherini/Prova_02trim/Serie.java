package sistemaserie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Serie {
    private String name;
    private String language;
    private List<String> genres;
    private Rating rating;
    private String status;
    private String premiered;
    private String ended;
    private Network network;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(name).append("\n");
        sb.append("Idioma: ").append(language).append("\n");
        sb.append("Gêneros: ").append(genres).append("\n");
        sb.append("Nota: ").append(rating != null && rating.getAverage() != null ? rating.getAverage() : "Sem nota").append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Estreia: ").append(premiered != null ? premiered : "Desconhecida").append("\n");
        sb.append("Fim: ").append(ended != null ? ended : "Ainda em exibição").append("\n");
        sb.append("Emissora: ").append(network != null ? network.getName() : "Desconhecida").append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Serie)) return false;
        Serie other = (Serie) obj;
        return name != null && name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
