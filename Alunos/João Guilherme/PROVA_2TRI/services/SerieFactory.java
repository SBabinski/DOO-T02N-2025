import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tv.sistema.models.Serie;

public class SerieFactory {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Serie criarSerie(String corpoJson) {
        if (corpoJson == null || corpoJson.isEmpty()) {
            System.out.println("Erro: Resposta vazia ou inválida.");
            return null; 
        }

        try {
            return mapper.readValue(corpoJson, Serie.class);
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao converter JSON em série única: " + e.getMessage());
            return null;
        }
    }
}
