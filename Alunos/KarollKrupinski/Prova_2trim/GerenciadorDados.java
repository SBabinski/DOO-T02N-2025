import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class GerenciadorDados {
    private static final String NOME_ARQUIVO = "dados.json";
    private Gson gson;

    public GerenciadorDados() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public DadosApp carregarDados() {
        try (Reader reader = new FileReader(NOME_ARQUIVO)) {
            DadosApp dados = gson.fromJson(reader, DadosApp.class);
            if (dados == null) {
                return new DadosApp();
            }
            return dados;
        } catch (IOException e) {
            return new DadosApp();
        }
    }

    public boolean salvarDados(DadosApp dados) {
        try (Writer writer = new FileWriter(NOME_ARQUIVO)) {
            gson.toJson(dados, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}