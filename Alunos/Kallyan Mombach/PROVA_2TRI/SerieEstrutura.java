import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerieEstrutura {
     private static final ObjectMapper mapper = new ObjectMapper();

    public static Serie criarSerie(String corpoJson) {
        try {
            return mapper.readValue(corpoJson, Serie.class);
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao converter JSON em série única: " + e.getMessage());
            return null;
        }
    }

    public static List<Serie> criarListaDeSeries(String corpoJson) {
        try {
            List<WrapperDeSerie> resultados = mapper.readValue(corpoJson, new TypeReference<List<WrapperDeSerie>>() {});
            return resultados.stream().map(WrapperDeSerie::getShow).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao converter JSON em lista de séries: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Classe auxiliar interna para mapear a estrutura da API
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WrapperDeSerie {
        private Serie show;

        public Serie getShow() {
            return show;
        }
    }
}

