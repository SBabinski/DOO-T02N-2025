import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonParserService {
    public static List<Serie> parseJsonParaListaSeries(String json) {
        List<Serie> listaDeSeries = new ArrayList<>();

        try {
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject resultadoBusca = jsonArray.get(i).getAsJsonObject();

                if (!resultadoBusca.has("show")) {
                    continue;
                }

                JsonObject serieJson = resultadoBusca.get("show").getAsJsonObject();

                String nome = getStringOrNull(serieJson, "name");
                String idioma = getStringOrNull(serieJson, "language");

                String genero = "";
                if (serieJson.has("genres") && serieJson.get("genres").isJsonArray() &&
                        serieJson.get("genres").getAsJsonArray().size() > 0) {
                    genero = serieJson.get("genres").getAsJsonArray().get(0).getAsString();
                }

                double nota = 0.0;
                if (serieJson.has("rating") && !serieJson.get("rating").isJsonNull() &&
                        serieJson.get("rating").isJsonObject()) {
                    JsonObject ratingObj = serieJson.get("rating").getAsJsonObject();
                    if (ratingObj.has("average") && !ratingObj.get("average").isJsonNull()) {
                        nota = ratingObj.get("average").getAsDouble();
                    }
                }

                String estado = getStringOrNull(serieJson, "status");
                LocalDate dataLancamento = getDateOrNull(serieJson, "premiered");
                LocalDate dataTermino = getDateOrNull(serieJson, "ended");

                String emissora = "";
                if (serieJson.has("network") && !serieJson.get("network").isJsonNull() &&
                        serieJson.get("network").isJsonObject()) {
                    emissora = getStringOrNull(serieJson.get("network").getAsJsonObject(), "name");
                }

                Serie serie = new Serie(nome, idioma, genero, nota, estado, dataLancamento,
                        dataTermino, emissora);
                listaDeSeries.add(serie);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return listaDeSeries;
    }

    private static String getStringOrNull(JsonObject obj, String field) {
        if (obj.has(field) && !obj.get(field).isJsonNull()) {
            return obj.get(field).getAsString();
        }
        return null;
    }

    private static LocalDate getDateOrNull(JsonObject obj, String field) {
        String dateStr = getStringOrNull(obj, field);

        if (dateStr != null && !dateStr.isEmpty()) {
            return LocalDate.parse(dateStr);
        }
        return null;
    }
}