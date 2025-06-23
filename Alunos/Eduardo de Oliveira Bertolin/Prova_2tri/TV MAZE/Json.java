import com.google.gson.Gson;
import java.io.*;

// salvar e carregar dados do usuário e das séries em arquivos JSON
public class Json {
    // onde o usuário será salvo
    private static final String USUARIO_ARQ = "usuario.json";

    // onde as séries serão salvas
    private static final String SERIES_ARQ = "series.json";

    // conversão entre objetos Java e JSON
    private static final Gson gson = new Gson();

    // carrega os dados do usuário do arquivo usuario.json
    public static Usuario carregarUsuario() {
        try (FileReader reader = new FileReader(USUARIO_ARQ)) {
            // converte o conteúdo do arquivo JSON para um objeto Usuario

            return gson.fromJson(reader, Usuario.class);
        } catch (Exception e) {
            // se erro retorna null
            return null;
        }
    }

    // salva os dados do usuário no arquivo usuario.json
    public static void salvarUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter(USUARIO_ARQ)) {
            // converte o objeto Usuario em JSON e grava no arquivo
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            // se erro, imprime a exceção no console
            e.printStackTrace();
        }
    }

    // carrega os dados das séries a partir do arquivo series.json
    public static SerieManager carregarSeries() {
        try (FileReader reader = new FileReader(SERIES_ARQ)) {
            // converte o conteúdo do arquivo JSON para um objeto SerieManager
            return gson.fromJson(reader, SerieManager.class);
        } catch (Exception e) {
            // se erro retorna null
            return null;
        }
    }

    // Salva os dados das séries no arquivo series.json
    public static void salvarSeries(SerieManager manager) {
        try (FileWriter writer = new FileWriter(SERIES_ARQ)) {
            // converte o objeto SerieManager em JSON e grava no arquivo
            gson.toJson(manager, writer);
        } catch (IOException e) {
            // se erro, imprime a exceção no console
            e.printStackTrace();
        }
    }
}