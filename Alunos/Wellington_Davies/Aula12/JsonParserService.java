import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

public class JsonParserService {

    public static Clima parseJson(String json) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject currentConditions = jsonObject.getAsJsonObject("currentConditions");

            double temperaturaAtual = getDoubleSafe(currentConditions, "temp", 0.0);
            double tempMax = getDoubleSafeFromDay(jsonObject, "tempmax", 0.0);
            double tempMin = getDoubleSafeFromDay(jsonObject, "tempmin", 0.0);
            double umidade = getDoubleSafe(currentConditions, "humidity", 0.0);
            String condicao = getStringSafe(currentConditions, "conditions", "Indefinido");
            double precipitacao = getDoubleSafe(currentConditions, "precip", 0.0);
            double velocidadeVento = getDoubleSafe(currentConditions, "windspeed", 0.0);
            double direcaoVento = getDoubleSafe(currentConditions, "winddir", 0.0);

            return new Clima(temperaturaAtual, tempMax, tempMin, umidade, condicao, precipitacao, velocidadeVento, direcaoVento);
        } catch (JsonSyntaxException | NullPointerException e) {
            throw new RuntimeException("Erro ao analisar o JSON: " + e.getMessage(), e);
        }
    }

    // Busca double com segurança em um objeto
    private static double getDoubleSafe(JsonObject obj, String memberName, double defaultValue) {
        JsonElement el = obj.get(memberName);
        if (el != null && !el.isJsonNull()) {
            try {
                return el.getAsDouble();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    // Busca double com segurança no primeiro dia do array "days"
    private static double getDoubleSafeFromDay(JsonObject root, String memberName, double defaultValue) {
        if (root.has("days") && root.get("days").isJsonArray() && root.get("days").getAsJsonArray().size() > 0) {
            JsonObject day = root.get("days").getAsJsonArray().get(0).getAsJsonObject();
            return getDoubleSafe(day, memberName, defaultValue);
        }
        return defaultValue;
    }

    // Busca string com segurança em um objeto
    private static String getStringSafe(JsonObject obj, String memberName, String defaultValue) {
        JsonElement el = obj.get(memberName);
        if (el != null && !el.isJsonNull()) {
            try {
                String value = el.getAsString();
                if (!value.isEmpty()) {
                    return value;
                }
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}