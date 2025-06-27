package org.series.json;
import org.series.Usuario;
import java.io.*;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    public static void salvarUsuario(Usuario u, String caminho) {
        try {
            Path path = Paths.get(caminho);
            Files.createDirectories(path.getParent());

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.write(path, gson.toJson(u).getBytes());
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }


    public static Usuario carregarUsuario(String caminho) {
        try {
            if (!Files.exists(Paths.get(caminho))) return null;
            String json = new String(Files.readAllBytes(Paths.get(caminho)));
            return new Gson().fromJson(json, Usuario.class);
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
            return null;
        }
    }



}