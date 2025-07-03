import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ClimaFormatter {

    public void mostrarDados(String json) {
        JsonObject raiz = JsonParser.parseString(json).getAsJsonObject();
        JsonObject atual = raiz.getAsJsonObject("currentConditions");

        double temp = atual.get("temp").getAsDouble();
        double tempMax = raiz.getAsJsonArray("days").get(0).getAsJsonObject().get("tempmax").getAsDouble();
        double tempMin = raiz.getAsJsonArray("days").get(0).getAsJsonObject().get("tempmin").getAsDouble();
        double umidade = atual.get("humidity").getAsDouble();
        String condicaoIngles = atual.get("conditions").getAsString();
        String condicaoPt = traduzirCondicao(condicaoIngles);      
        double precipitacao = atual.get("precip").getAsDouble();
        double vento = atual.get("windspeed").getAsDouble();
        double direcaoVento = atual.get("winddir").getAsDouble();

        System.out.println("\nClima em " + raiz.get("resolvedAddress").getAsString());
        System.out.println("Temperatura atual: " + temp + " °C");
        System.out.println("Máxima do dia: " + tempMax + " °C");
        System.out.println("Mínima do dia: " + tempMin + " °C");
        System.out.println("Umidade do ar: " + umidade + "%");
        System.out.println("Condição: " + condicaoPt);
        System.out.println("Precipitação: " + precipitacao + " mm");
        System.out.println("Vento: " + vento + " km/h, direção " + direcaoVento + "°");
    }

    private String traduzirCondicao(String condicaoIngles) {
        condicaoIngles = condicaoIngles.toLowerCase();

        if (condicaoIngles.contains("clear")) return "Limpo";
        if (condicaoIngles.contains("cloudy")) return "Nublado";
        if (condicaoIngles.contains("partially cloudy")) return "Parcialmente nublado";
        if (condicaoIngles.contains("rain")) return "Chuva";
        if (condicaoIngles.contains("snow")) return "Neve";
        if (condicaoIngles.contains("overcast")) return "Encoberto";
        if (condicaoIngles.contains("thunderstorm")) return "Trovoadas";
        if (condicaoIngles.contains("fog")) return "Nevoeiro";
        if (condicaoIngles.contains("drizzle")) return "Garoa";

        return condicaoIngles;
    }
}
