package sistemaSerie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static <T> void salvar(String caminhoArquivo, T objeto) throws IOException {
        try (Writer writer = new FileWriter(caminhoArquivo)) {
            gson.toJson(objeto, writer);
        }
    }


    public static <T> T carregar(String caminhoArquivo, Class<T> clazz) throws IOException {
        try (Reader reader = new FileReader(caminhoArquivo)) {
            return gson.fromJson(reader, clazz);
        }
    }
}
