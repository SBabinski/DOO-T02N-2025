import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Salvar {

    private static final String CAMINHO = "usuario.json";  

    public static void salvar(Usuario usuario) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
            FileWriter writer = new FileWriter(CAMINHO); 
            gson.toJson(usuario, writer); 
            writer.close(); 
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    
    public static Usuario carregar() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(CAMINHO); 
            Usuario usuario = gson.fromJson(reader, Usuario.class); 
            reader.close();
            return usuario;
        } catch (IOException e) {
            return null; 
        }
    }
}
