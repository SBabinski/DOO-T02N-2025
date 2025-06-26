
import java.nio.file.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

public class NoticiaRepository {
    private static final String FILE = "dados.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> carregar() {
        try {
            if (!Files.exists(Path.of(FILE))) return new HashMap<>();
            return mapper.readValue(Files.readString(Path.of(FILE)), new TypeReference<>() {});
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static void salvar(Map<String, Object> dados) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dados);
            Files.writeString(Path.of(FILE), json);
        } catch (Exception e) {
            System.out.println("Erro ao salvar os dados.");
        }
    }
}
