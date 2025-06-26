import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
      private static final ObjectMapper mapper = new ObjectMapper();
    private static final String caminho = "data/perfil.json";
    private static final File arquivo = new File(caminho);

    public static boolean salvarPerfil(Perfil perfil) {
        try {
            arquivo.getParentFile().mkdirs();
            mapper.writeValue(arquivo, perfil);
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar o perfil: " + e.getMessage());
            return false;
        }
    }

    public static Perfil carregarPerfil() {
        try {
            return mapper.readValue(arquivo, Perfil.class);
        } catch (IOException e) {
            System.out.println("Erro ao carregar o perfil: " + e.getMessage());
            return null;
        }
    }
}
