import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class PersistenciaDados {
    private static final String ARQUIVO = "usuario.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salvarUsuario(Usuario usuario) {
        try (Writer writer = new FileWriter(ARQUIVO)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public static Usuario carregarUsuario() {
        try (Reader reader = new FileReader(ARQUIVO)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            return null;
        }
    }
}
