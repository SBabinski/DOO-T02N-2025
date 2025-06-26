
import java.net.http.*;
import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class IBGEApiClient {
    private static final String API_URL = "https://servicodados.ibge.gov.br/api/v3/noticias/";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Noticia> buscarNoticias(String palavra) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = API_URL + "?busca=" + palavra;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = mapper.readTree(response.body()).get("items");
        return mapper.readerForListOf(Noticia.class).readValue(root);
    }
}
