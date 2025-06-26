import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONParserService {
    private Gson gson;
    private boolean salvarDebug;
    
    public JSONParserService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.salvarDebug = false; // Altere para true se quiser salvar JSONs automaticamente
    }
    
    /**
     * Faz o parsing do JSON da API Visual Crossing e retorna um objeto WeatherData
     * @param jsonResponse String JSON da resposta da API
     * @return WeatherData com os dados parseados
     * @throws Exception se houver erro no parsing
     */
    public WeatherData parseWeatherData(String jsonResponse) throws Exception {
        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            throw new Exception("Resposta JSON vazia ou nula");
        }
        
        try {
            if (salvarDebug) {
                salvarJsonParaArquivo(jsonResponse, gerarNomeArquivo());
            }
            
            // Parsear o JSON principal
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            
            WeatherData weatherData = new WeatherData();
            
            extrairDadosLocalizacao(jsonObject, weatherData);
            
            extrairCondicoesAtuais(jsonObject, weatherData);
            
            extrairDadosDia(jsonObject, weatherData);
            
            if (!weatherData.isValido()) {
                throw new Exception("Dados essenciais não encontrados no JSON");
            }
            
            return weatherData;
            
        } catch (JsonSyntaxException e) {
            throw new Exception("Erro ao parsear JSON: formato inválido", e);
        } catch (Exception e) {
            throw new Exception("Erro ao processar dados do clima: " + e.getMessage(), e);
        }
    }
    
    private void extrairDadosLocalizacao(JsonObject jsonObject, WeatherData weatherData) {
        if (jsonObject.has("resolvedAddress")) {
            weatherData.setCidade(getStringSeguro(jsonObject, "resolvedAddress"));
        } else if (jsonObject.has("address")) {
            weatherData.setCidade(getStringSeguro(jsonObject, "address"));
        }
    }

    private void extrairCondicoesAtuais(JsonObject jsonObject, WeatherData weatherData) {
        if (jsonObject.has("currentConditions")) {
            JsonObject current = jsonObject.getAsJsonObject("currentConditions");
            
            weatherData.setTemperaturaAtual(getDoubleSeguro(current, "temp"));
            
            weatherData.setUmidade(getDoubleSeguro(current, "humidity"));
            
            weatherData.setCondicaoTempo(getStringSeguro(current, "conditions"));
            
            weatherData.setPrecipitacao(getDoubleSeguro(current, "precip"));
            
            weatherData.setVelocidadeVento(getDoubleSeguro(current, "windspeed"));
            
            double direcaoGraus = getDoubleSeguro(current, "winddir");
            weatherData.setDirecaoVento(WeatherData.converterDirecaoVento(direcaoGraus));
            
            weatherData.setDataHora(getStringSeguro(current, "datetime"));
        }
    }
    
    private void extrairDadosDia(JsonObject jsonObject, WeatherData weatherData) {
        if (jsonObject.has("days")) {
            JsonArray days = jsonObject.getAsJsonArray("days");
            
            if (days.size() > 0) {
                JsonObject today = days.get(0).getAsJsonObject();
                
                weatherData.setTemperaturaMaxima(getDoubleSeguro(today, "tempmax"));
                
                weatherData.setTemperaturaMinima(getDoubleSeguro(today, "tempmin"));
                
                // Se não tiver condições atuais, usar do dia
                if (weatherData.getCondicaoTempo() == null || weatherData.getCondicaoTempo().equals("N/A")) {
                    weatherData.setCondicaoTempo(getStringSeguro(today, "conditions"));
                }
                
                // Se não tiver data/hora atual, usar do dia
                if (weatherData.getDataHora() == null || weatherData.getDataHora().equals("N/A")) {
                    weatherData.setDataHora(getStringSeguro(today, "datetime"));
                }
            }
        }
    }
    
    private String getStringSeguro(JsonObject jsonObject, String campo) {
        if (jsonObject.has(campo) && !jsonObject.get(campo).isJsonNull()) {
            return jsonObject.get(campo).getAsString();
        }
        return "N/A";
    }
    
    private double getDoubleSeguro(JsonObject jsonObject, String campo) {
        if (jsonObject.has(campo) && !jsonObject.get(campo).isJsonNull()) {
            JsonElement element = jsonObject.get(campo);
            try {
                return element.getAsDouble();
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter " + campo + " para double: " + element.getAsString());
                return 0.0;
            }
        }
        return 0.0;
    }
    
    public void salvarJsonParaArquivo(String jsonResponse, String nomeArquivo) {
        try (java.io.FileWriter writer = new java.io.FileWriter(nomeArquivo)) {
            // Formatar JSON de forma legível
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            writer.write(gson.toJson(jsonObject));
            System.out.println("JSON salvo em: " + nomeArquivo);
        } catch (Exception e) {
            System.err.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }
    
    private String gerarNomeArquivo() {
        LocalDateTime agora = LocalDateTime.now();
        String timestamp = agora.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "debug_response_" + timestamp + ".json";
    }
    
    public boolean validarFormatoJson(String jsonResponse) {
        try {
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            return jsonObject.has("resolvedAddress") || jsonObject.has("address");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void setSalvarDebug(boolean salvarDebug) {
        this.salvarDebug = salvarDebug;
    }
}