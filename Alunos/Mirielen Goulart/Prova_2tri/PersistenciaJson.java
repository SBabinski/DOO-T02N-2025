package seriesTV;

import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PersistenciaJson {
    private static final String ARQUIVO = "usuario.json";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                }
            })
            .setPrettyPrinting()
            .create();

    public void salvar(Usuario usuario) {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            gson.toJson(usuario, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public Usuario carregar() {
        try (FileReader reader = new FileReader(ARQUIVO)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            if (usuario == null) {
                System.out.println("Arquivo vazio. Criando novo usuário.");
                return null;
            }
            return usuario;
        } catch (Exception e) {
            System.out.println("Nenhum dado salvo encontrado. Criando novo usuário.");
            return null;
        }
    }
}
