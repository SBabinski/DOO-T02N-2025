
import java.net.HttpURLConnection;  
import java.net.URL;               
import java.util.Scanner;          
import com.google.gson.Gson;       

public class Funcionar {
    
    private static final String BASE_URL = "https://api.tvmaze.com"; 

    public static Serie buscarSeriePorNome(String nomeSerie) {
        try {
        
            String urlCompleta = BASE_URL + "/search/shows?q=" + nomeSerie.replace(" ", "%20");
            
            URL url = new URL(urlCompleta); 
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET"); // cria conexão e o get busca os dados

            Scanner scanner = new Scanner(conexao.getInputStream());
            String jsonResposta = scanner.useDelimiter("\\A").next(); // scanner le o que foi procurado e salva
            scanner.close(); 
            
            Gson gson = new Gson();
            ResultadoBusca[] resultados = gson.fromJson(jsonResposta, ResultadoBusca[].class); // objeto e vai pra classe 

            if (resultados.length > 0 && resultados[0].getShow() != null) {
            return resultados[0].getShow(); 
            } else {
            return null; // pesquisa e mostra o que foi encontrado
}
        } catch (Exception e) {
            System.out.println("Erro ao buscar série: " + e.getMessage());
            return null;
        }
    }
}