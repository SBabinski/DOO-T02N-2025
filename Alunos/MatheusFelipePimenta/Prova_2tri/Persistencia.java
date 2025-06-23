import com.google.gson.reflect.TypeToken;
import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Persistencia {
    private static final String CAMINHO = "series.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salvar(Map<String, List<Serie>> dados) {
        try (Writer writer = new FileWriter(CAMINHO)) {
            gson.toJson(dados, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static Map<String, List<Serie>> carregar() {
        try (Reader reader = new FileReader(CAMINHO)) {
            Type tipo = new TypeToken<Map<String, List<Serie>>>(){}.getType();
            return gson.fromJson(reader, tipo);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
