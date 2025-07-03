import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlBuilder {
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
    private static final String UNIT_GROUP = "metric"; // Para usar Celsius
    private static final String INCLUDE = "current"; // Incluir condições atuais
    private static final String CONTENT_TYPE = "json";
    
    /**
     * Constrói a URL completa para a requisição da API
     * @param cidade Nome da cidade para consulta
     * @return URL formatada para a API
     * @throws Exception se houver erro na construção da URL
     */
    public static String construirUrl(String cidade) throws Exception {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da cidade não pode ser vazio");
        }
        
        // Verificar se a chave está configurada
        if (!Chave.isChaveConfigurada()) {
            throw new RuntimeException("Chave da API não configurada");
        }
        
        try {
            // Codificar o nome da cidade para URL
            String cidadeCodificada = URLEncoder.encode(cidade.trim(), StandardCharsets.UTF_8.toString());
            
            // Construir URL com parâmetros
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(BASE_URL)
                     .append("/")
                     .append(cidadeCodificada)
                     .append("?unitGroup=").append(UNIT_GROUP)
                     .append("&include=").append(INCLUDE)
                     .append("&key=").append(Chave.getApiKey())
                     .append("&contentType=").append(CONTENT_TYPE);
            
            return urlBuilder.toString();
            
        } catch (Exception e) {
            throw new Exception("Erro ao construir URL: " + e.getMessage(), e);
        }
    }
    
    /**
     * Constrói URL para consulta de múltiplos dias (opcional)
     * @param cidade Nome da cidade
     * @param dias Número de dias para consultar
     * @return URL formatada
     * @throws Exception se houver erro na construção
     */
    public static String construirUrlComDias(String cidade, int dias) throws Exception {
        if (dias <= 0 || dias > 15) {
            throw new IllegalArgumentException("Número de dias deve ser entre 1 e 15");
        }
        
        String urlBase = construirUrl(cidade);
        return urlBase.replace("&include=current", "&include=current,days&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir");
    }
    
    /**
     * Valida se uma URL está bem formada
     * @param url URL para validar
     * @return true se a URL parece válida
     */
    public static boolean validarUrl(String url) {
        return url != null && 
               url.startsWith("https://weather.visualcrossing.com") && 
               url.contains("key=") && 
               url.contains("contentType=json");
    }
}