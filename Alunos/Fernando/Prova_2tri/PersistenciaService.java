package Prova_2tri;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;

public class PersistenciaService {

    private final String caminho;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public PersistenciaService() {
        this.caminho = "C:/Users/prosk/OneDrive/Documentos/GitHub/DOO-T02N-2025/Alunos/Fernando/Trabalho/data/usuario.json";
    }

    public void salvar(Usuario usuario) throws IOException {
        File diretorio = new File("data");
       
        try (Writer writer = new FileWriter(caminho)) {
            gson.toJson(usuario, writer);
        }
    }

    public Usuario carregar() throws IOException {
        File file = new File(caminho);
        if (!file.exists()) {
            return null;
        }
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Usuario>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }

}