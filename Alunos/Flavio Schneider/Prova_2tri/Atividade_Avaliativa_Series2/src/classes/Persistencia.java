package classes;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Persistencia {

    private static final String CAMINHO_ARQUIVO = "series.json";
    private static final String ARQUIVO_USUARIO = "usuario.json";

    public static void salvarSeries(List<Serie> series) {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            Gson gson = new Gson();
            gson.toJson(series, writer);
            System.out.println("✔ Séries salvas com sucesso no arquivo.");
        } catch (Exception e) {
            System.out.println("Erro ao salvar as séries: " + e.getMessage());
        }
    }

    public static List<Serie> carregarSeries() {
        try (Reader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<Serie>>(){}.getType());
        } catch (Exception e) {
            System.out.println("Nenhuma série salva ou erro ao carregar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static void salvarUsuario(Usuario usuario) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(ARQUIVO_USUARIO)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    
    public static Usuario carregarUsuario() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(ARQUIVO_USUARIO)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            return usuario;
        } catch (IOException e) {
            System.out.println("Arquivo de usuário não encontrado, criando novo usuário.");
            return new Usuario("Usuário Padrão");
        }
        
    }
}