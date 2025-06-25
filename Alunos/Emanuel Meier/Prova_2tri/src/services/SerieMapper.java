package services;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import models.Serie;
import java.util.List;
import java.util.ArrayList;

public class SerieMapper {

    public static Serie singleSerieMapper(JsonObject json) {
        if (json == null) return null;

        Serie serie = new Serie();

        serie.setName(getAsString(json, "name"));
        serie.setLanguage(getAsString(json, "language"));

        // Gêneros 
        if (json.has("genres") && json.get("genres").isJsonArray()) {
            JsonArray genresArray = json.getAsJsonArray("genres");
            List<String> genres = new ArrayList<>();
            for (JsonElement g : genresArray) {
                genres.add(g.getAsString());
            }
            serie.setGenres(genres);
        }

        // Nota 
        if (json.has("rating") && json.getAsJsonObject("rating").has("average")) {
            JsonElement ratingElem = json.getAsJsonObject("rating").get("average");
            if (!ratingElem.isJsonNull()) {
                try {
                    serie.setRating(ratingElem.getAsDouble());
                } catch (Exception e) {
                    serie.setRating(0);
                }
            } else {
                serie.setRating(0);
            }
        } else {
            serie.setRating(0);
        }

        serie.setStatus(getAsString(json, "status"));
        serie.setPremiered(parseDate(json, "premiered"));
        serie.setEnded(parseDate(json, "ended"));

        // Emissora 
        if (json.has("network") && json.get("network").isJsonObject()) {
            JsonObject networkObj = json.getAsJsonObject("network");
            String networkName = getAsString(networkObj, "name");
            serie.setNetwork(networkName);
        } else {
            serie.setNetwork("N/A");
        }

        return serie;
    }

    private static String getAsString(JsonObject obj, String memberName) {
        if (obj.has(memberName) && !obj.get(memberName).isJsonNull()) {
            return obj.get(memberName).getAsString();
        }
        return "N/A";
    }


    private static Long parseDate(JsonObject json, String memberName) {
        if (json.has(memberName) && !json.get(memberName).isJsonNull()) {
            try {
                JsonElement elem = json.get(memberName);
                if (elem.isJsonPrimitive()) {
                    JsonPrimitive prim = elem.getAsJsonPrimitive();
                    if (prim.isNumber()) {
                        // Se for número, retorna o valor long diretamente
                        return prim.getAsLong();
                    } else if (prim.isString()) {
                        // Se for string, tenta parsear a data
                        String dateStr = prim.getAsString();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = sdf.parse(dateStr);
                        return date.getTime();
                    }
                }
            } catch (Exception e) {
                // Se erro, retorna null (sem data)
                return null;
            }
        }
        return null;
    }
}
