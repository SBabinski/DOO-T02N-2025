package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Api {

	public static void main(String[] args) {
		
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("Digite o nome da serie: ");
//		String serieNome = scanner.nextLine();
//		
//		Api.buscarSerie(serieNome);
    }
	
	
	
	
	public static Serie buscarSerie(String serieNome) {
		
        
        String urlString = "https://api.tvmaze.com/singlesearch/shows?q=" + serieNome;
        Serie serie = null;		
        		
        try {
            
            URL url = new URL(urlString);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(5000);
            conexao.setReadTimeout(5000);

           
            int respostaCode = conexao.getResponseCode();
            if (respostaCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            StringBuilder resposta = new StringBuilder();

            while ((linha = in.readLine()) != null) {
                resposta.append(linha);
            }
            
            in.close();
            
            
            Gson gson = new Gson();
            JsonObject obj = gson.fromJson(resposta.toString(), JsonObject.class);

            int id = obj.get("id").getAsInt();
            String nome = obj.get("name").getAsString();
            String idioma = obj.get("language").getAsString();
            String estado = obj.get("status").getAsString();
            String dataEstreia = obj.get("premiered").isJsonNull() ? "Não informado" : obj.get("premiered").getAsString();
            String dataFim =obj.get("ended").isJsonNull() ? "Não informado" : obj.get("ended").getAsString();
            double nota = obj.get("rating").getAsJsonObject().get("average").isJsonNull() ? 0.0 : obj.get("rating").getAsJsonObject().get("average").getAsDouble();
            JsonObject network = obj.getAsJsonObject("network");
            String emissora = network.get("name").getAsString();
            List<String> generos = new ArrayList<>();
            for (var g : obj.get("genres").getAsJsonArray()) {
                generos.add(g.getAsString());
            }
            
            
            System.out.println("\nInformações da Série:");
            System.out.println("Nome: " + nome);
            System.out.println("Idioma: " + idioma);
            System.out.println("Status: " + estado);
            System.out.println("Estreia: " + dataEstreia);
            System.out.println("Termino: " + dataFim);
            System.out.println("Nota: " + nota);
            System.out.println("Emissora: " + emissora);
            System.out.println("Gêneros: " + String.join(", ", generos));

            serie = new Serie(id, nome, idioma, generos, nota, estado, dataEstreia, dataFim, emissora);

         
         	List<Serie> listaSalva = Persistencia.carregarSeries();
            
            } else {
                System.out.println("Erro: código " + respostaCode);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar a série: " + e.getMessage());
            return null;
        }
        
        if (serie != null) {
            List<Serie> listaSalva = Persistencia.carregarSeries();
            boolean jaExiste = false;

            for (Serie s : listaSalva) {
                if (s.getId() == serie.getId()) {
                    jaExiste = true;
                    break;
                }
            }

            if (!jaExiste) {
                listaSalva.add(serie);
                Persistencia.salvarSeries(listaSalva);
            }
        }
        
		return serie;
	}
	
}