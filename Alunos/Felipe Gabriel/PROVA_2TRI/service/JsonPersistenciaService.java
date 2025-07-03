package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonPersistenciaService {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String arquivo = "usuario.json";

    public void salvar(Usuario usuario) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    public Usuario carregar() {
        try (FileReader reader = new FileReader(arquivo)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            return null; 
        }
    }
}

