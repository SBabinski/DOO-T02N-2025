import java.util.List;
import java.util.Map;

public class Serie {

    private String name;
    private String language;
    private List<String> genres;
    private Map<String, Object> rating;
    private String status;
    private String premiered;
    private String ended;
    private Network network;

    public static class Network {
        private String name;
        
        public String getName() {
            return name;
        }
    }
    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public double getRating() {
        if (rating != null && rating.get("average") != null) {
            return ((Number) rating.get("average")).doubleValue(); 
    } // conversão 
        return 0.0;
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

    public String getNetworkName() {
        return network != null ? network.getName() : "Desconhecida";
    }

    public String toString() {
    return String.format(
        "%s (%s) - Gêneros: %s\nNota: %.1f | Status: %s\nEstreia: %s | Término: %s | Emissora: %s",
        name,
        language,
        String.join(", ", genres),
        getRating(),
        status,
        premiered != null ? premiered : "Desconhecida",
        ended != null ? ended : "Em exibição",
        getNetworkName()
    );
}
@Override
public boolean equals(Object obj) {
    if (this == obj) return true; // for iguais retorna true
    if (obj == null || getClass() != obj.getClass()) return false;
    
    Serie outra = (Serie) obj;
    return name != null && name.equalsIgnoreCase(outra.name); // retornar independente de letras maiusculas
}

@Override
public int hashCode() {
    return name != null ? name.toLowerCase().hashCode() : 0;
}


}
