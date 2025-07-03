package prova2tri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class JsonStorage {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveUser(User user, String nomeArquivo) throws IOException {
        try (Writer writer = new FileWriter(nomeArquivo)) {
            gson.toJson(user, writer);
        }
    }

    public static User loadUser(String nomeArquivo) {
        try (Reader reader = new FileReader(nomeArquivo)) {
            return gson.fromJson(reader, User.class);
        } catch (IOException e) {
            return null;
        }
    }
}
