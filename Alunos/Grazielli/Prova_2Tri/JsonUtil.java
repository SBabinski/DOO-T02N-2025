package prova2tri;

import com.google.gson.*;
import java.io.*;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String PASTA_DADOS = "dados";

    public static void salvar(Usuario usuario) {
        try {
            File pasta = new File(PASTA_DADOS);
            if (!pasta.exists()) {
                boolean criada = pasta.mkdirs();
                if (!criada) {
                    System.out.println("Erro ao criar pasta de dados.");
                    return;
                }
            }

            String nomeArquivo = PASTA_DADOS + "/" + usuario.getNome().toLowerCase() + ".json";
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                gson.toJson(usuario, writer);
                System.out.println("Usuário salvo com sucesso!");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public static Usuario carregar(String nome) {
        String nomeArquivo = PASTA_DADOS + "/" + nome.toLowerCase() + ".json";
        File file = new File(nomeArquivo);

        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Usuario.class);
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuário: " + e.getMessage());
            return null;
        }
    }
}
