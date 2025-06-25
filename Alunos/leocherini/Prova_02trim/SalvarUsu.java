package sistemaSerie;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class SalvarUsu {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void salvar(Usuario usuario) {
        try {
            mapper.writeValue(new File("usuario.json"), usuario);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static Usuario carregar() {
        try {
            return mapper.readValue(new File("usuario.json"), Usuario.class);
        } catch (IOException e) {
            return null;
        }
    }
}