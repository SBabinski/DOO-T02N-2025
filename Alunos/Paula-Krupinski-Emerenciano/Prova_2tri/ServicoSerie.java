import com.google.gson.*;

public class ServicoSerie {
    public static void exibirSeries(String json) {
        JsonArray resultados = JsonParser.parseString(json).getAsJsonArray();
        Gson gson = new Gson();

        for (JsonElement elemento : resultados) {
            JsonObject show = elemento.getAsJsonObject().getAsJsonObject("show");
            Serie serie = gson.fromJson(show, Serie.class);

            System.out.println("\n----------------------------");
            System.out.println("📺 Nome: " + serie.getNome());
            System.out.println("🌐 Idioma: " + serie.getIdioma());
            System.out.println("🎭 Gêneros: " + String.join(", ", serie.getGeneros()));
            System.out.println("⭐ Nota geral: " + serie.getNota());
            System.out.println("📡 Estado: " + serie.getStatus());
            System.out.println("📆 Estreia: " + serie.getDataEstreia());
            System.out.println("🏁 Fim: " + serie.getDataTermino());
            System.out.println("🏢 Emissora: " + serie.getEmissora());
        }
    }
}
