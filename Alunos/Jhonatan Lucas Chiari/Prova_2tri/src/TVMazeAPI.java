import com.google.gson.*;
import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TVMazeAPI {
    private static final String BASE_URL = "https://api.tvmaze.com";
    private HttpClient httpClient;

    //chamada API
    public TVMazeAPI() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
    }

    public List<Serie> buscarSeries(String nome) {
        List<Serie> series = new ArrayList<>();
        
        try {
            // monta a URL de busca com encoding adequado
            String urlString = BASE_URL + "/search/shows?q=" + 
                             java.net.URLEncoder.encode(nome, "UTF-8");
            
            // requisição HTTP
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Accept", "application/json")
                .header("User-Agent", "SeriesApp/1.0")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

            System.out.println("Fazendo requisição para: " + urlString);

            // envia a requisição e recebe a resposta
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Status da resposta: " + response.statusCode());
            
            if (response.statusCode() == 200) {
                // parse do JSON para objetos Serie
                String responseBody = response.body();
                
                if (responseBody != null && !responseBody.trim().isEmpty()) {
                    JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
                    
                    System.out.println("Processando " + jsonArray.size() + " resultados...");
                    
                    for (JsonElement element : jsonArray) {
                        JsonObject searchResult = element.getAsJsonObject();
                        JsonObject show = searchResult.getAsJsonObject("show");
                        
                        Serie serie = parsearSerie(show);
                        if (serie != null) {
                            series.add(serie);
                        }
                    }
                } else {
                    System.out.println("Resposta vazia da API");
                }
            } else {
                System.out.println("Erro na API: " + response.statusCode());
                System.out.println("Resposta: " + response.body());
            }
            
        } catch (java.net.ConnectException e) {
            System.out.println("Erro de conexão: Verifique sua internet");
        } catch (java.net.http.HttpTimeoutException e) {
            System.out.println("Timeout: A API demorou muito para responder");
        } catch (java.io.UnsupportedEncodingException e) {
            System.out.println("Erro de codificação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
            e.printStackTrace(); // Para debug
        }
        
        return series;
    }

    private Serie parsearSerie(JsonObject show) {
        try {
            // ID da série
            int id = show.has("id") && !show.get("id").isJsonNull() 
                   ? show.get("id").getAsInt() : 0;
            
            // Nome da série
            String nome = show.has("name") && !show.get("name").isJsonNull() 
                        ? show.get("name").getAsString() : "Nome não disponível";
            
            // Idioma
            String idioma = show.has("language") && !show.get("language").isJsonNull() 
                          ? show.get("language").getAsString() : null;
            
            // Gêneros
            List<String> generos = new ArrayList<>();
            if (show.has("genres") && show.get("genres").isJsonArray()) {
                JsonArray generosArray = show.getAsJsonArray("genres");
                for (JsonElement genero : generosArray) {
                    if (!genero.isJsonNull()) {
                        generos.add(genero.getAsString());
                    }
                }
            }
            
            // Nota/Rating
            double nota = 0.0;
            if (show.has("rating") && !show.get("rating").isJsonNull()) {
                JsonObject rating = show.getAsJsonObject("rating");
                if (rating.has("average") && !rating.get("average").isJsonNull()) {
                    nota = rating.get("average").getAsDouble();
                }
            }
            
            // Status da série
            String status = show.has("status") && !show.get("status").isJsonNull() 
                          ? show.get("status").getAsString() : null;
            
            // Data de estreia
            String dataEstreia = null;
            if (show.has("premiered") && !show.get("premiered").isJsonNull()) {
                dataEstreia = show.get("premiered").getAsString();
            }
            
            // Data de término
            String dataTermino = null;
            if (show.has("ended") && !show.get("ended").isJsonNull()) {
                dataTermino = show.get("ended").getAsString();
            }
            
            // Emissora/Network
            String emissora = null;
            if (show.has("network") && !show.get("network").isJsonNull()) {
                JsonObject network = show.getAsJsonObject("network");
                if (network.has("name") && !network.get("name").isJsonNull()) {
                    emissora = network.get("name").getAsString();
                }
            } else if (show.has("webChannel") && !show.get("webChannel").isJsonNull()) {
                JsonObject webChannel = show.getAsJsonObject("webChannel");
                if (webChannel.has("name") && !webChannel.get("name").isJsonNull()) {
                    emissora = webChannel.get("name").getAsString();
                }
            }
            
            // Resumo/Summary
            String resumo = null;
            if (show.has("summary") && !show.get("summary").isJsonNull()) {
                resumo = show.get("summary").getAsString();
                // Remove tags HTML do resumo
                resumo = resumo.replaceAll("<[^>]*>", "");
                // Remove entidades HTML comuns
                resumo = resumo.replace("&quot;", "\"")
                              .replace("&amp;", "&")
                              .replace("&lt;", "<")
                              .replace("&gt;", ">")
                              .replace("&#39;", "'");
            }
            
            return new Serie(id, nome, idioma, generos, nota, status, 
                           dataEstreia, dataTermino, emissora, resumo);
            
        } catch (Exception e) {
            System.out.println("Erro ao parsear série: " + e.getMessage());
            return null;
        }
    }

    // Método adicional para buscar série específica por ID (opcional)
    public Serie buscarSeriePorId(int id) {
        try {
            String urlString = BASE_URL + "/shows/" + id;
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Accept", "application/json")
                .header("User-Agent", "SeriesApp/1.0")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JsonObject show = JsonParser.parseString(response.body()).getAsJsonObject();
                return parsearSerie(show);
            } else {
                System.out.println("Erro ao buscar série por ID: " + response.statusCode());
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao buscar série por ID: " + e.getMessage());
        }
        
        return null;
    }

    public void close() {
        System.out.println("Recursos da API fechados");
    }
}