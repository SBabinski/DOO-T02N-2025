import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonParserService {
    public static List<Serie> parseJsonParaListaDeSeries(String json) {
        List<Serie> listaDeSeries = new ArrayList<>();
        
        try {
            // Parse do JSON para array
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
            // Itera sobre cada objeto no array

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject resultadoBusca = jsonArray.get(i).getAsJsonObject();
                
                //dados da série estão dentro do objeto "show"
                if (!resultadoBusca.has("show")) {
                    continue; // Pula se não tem o objeto "show"
                }
                
                JsonObject serieJson = resultadoBusca.get("show").getAsJsonObject();
                
                // Extrai os dados de cada série
                String serieName = serieJson.has("name") && !serieJson.get("name").isJsonNull()
                    ? serieJson.get("name").getAsString()
                    : "Nome não disponível";

                String idiomaSerie = serieJson.has("language") && !serieJson.get("language").isJsonNull()
                    ? serieJson.get("language").getAsString()
                    : "Idioma não disponível";

                String generoSerie = serieJson.has("genres") && serieJson.get("genres").isJsonArray() && serieJson.get("genres").getAsJsonArray().size() > 0
                    ? serieJson.get("genres").getAsJsonArray().get(0).getAsString()
                    : "Gênero não disponível";

                double notaSerie = serieJson.has("rating") && serieJson.get("rating").isJsonObject() && serieJson.get("rating").getAsJsonObject().has("average") && !serieJson.get("rating").getAsJsonObject().get("average").isJsonNull()
                    ? serieJson.get("rating").getAsJsonObject().get("average").getAsDouble()
                    : 0.0;

                String estadoSerie = serieJson.has("status") && !serieJson.get("status").isJsonNull()
                    ? serieJson.get("status").getAsString()
                    : "Estado não disponível";

                LocalDate dataDeLancamentoSerie = serieJson.has("premiered") && !serieJson.get("premiered").isJsonNull()
                    ? LocalDate.parse(serieJson.get("premiered").getAsString())
                    : null;

                LocalDate dataDeTerminoSerie = serieJson.has("ended") && !serieJson.get("ended").isJsonNull()
                    ? LocalDate.parse(serieJson.get("ended").getAsString())
                    : null;

                String emissoraSerie = serieJson.has("network") && serieJson.get("network").isJsonObject() && serieJson.get("network").getAsJsonObject().has("name") && !serieJson.get("network").getAsJsonObject().get("name").isJsonNull()
                    ? serieJson.get("network").getAsJsonObject().get("name").getAsString()
                    : "Emissora não disponível";

                // Verifica se existe a imagem e se é um objeto JSON válido
                String imagemUrl = serieJson.has("image") && serieJson.get("image").isJsonObject() && 
                                  serieJson.get("image").getAsJsonObject().has("medium") && 
                                  !serieJson.get("image").getAsJsonObject().get("medium").isJsonNull()
                    ? serieJson.get("image").getAsJsonObject().get("medium").getAsString()
                    : "Imagem não disponível";

                // Cria o objeto Serie com TODOS os parâmetros (incluindo imagemUrl)
                Serie serie = new Serie(serieName, idiomaSerie, generoSerie, notaSerie, estadoSerie, 
                                      dataDeLancamentoSerie, dataDeTerminoSerie, emissoraSerie, imagemUrl);
                listaDeSeries.add(serie);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            e.printStackTrace(); // Para debug
        }
        
        return listaDeSeries;
    }
}
