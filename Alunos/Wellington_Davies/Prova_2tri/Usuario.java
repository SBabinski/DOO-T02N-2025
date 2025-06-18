import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;

public class Usuario {

    private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método para salvar a saudação em um arquivo JSON
    public static void salvaraSaudacao(String saudacao, String caminhoArquivo) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
        gson.toJson(saudacao, writer);
        } catch (Exception e) {
            System.err.println("Erro ao salvar a saudação: " + e.getMessage());
        } 
    }

    // Método para carregar a saudação de um arquivo JSON
    public static String carregarSaudacao(String caminhoArquivo) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(caminhoArquivo)) {
            return gson.fromJson(reader, String.class);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a saudação: " + e.getMessage());
            return null;
        }
    }
}
