import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class BuscarSerie {

    private ConexaoApi conexaoApi;
    private Gson gson;

    public BuscarSerie() {
        this.conexaoApi = new ConexaoApi();
        this.gson = new Gson();
    }

    public List<Series> buscarSeries(String nomeSerie) {
        String jsonResposta = conexaoApi.buscarSeries(nomeSerie);
        List<Series> seriesEncontradas = new ArrayList<>();

        if (jsonResposta == null) {
            return seriesEncontradas;
        }

        try {
            JsonArray jsonArray = JsonParser.parseString(jsonResposta).getAsJsonArray();

            if (jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject showObject = jsonArray.get(i).getAsJsonObject().getAsJsonObject("show");
                    Series serie = gson.fromJson(showObject, Series.class);
                    seriesEncontradas.add(serie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return seriesEncontradas;
    }
}