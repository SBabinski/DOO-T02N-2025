import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClimaController {
    private ConexaoApi api;

    public ClimaController() {
        this.api = new ConexaoApi();
    }

    public Clima obterClimaPorCidade(String cidade) {
        String json = api.buscarClima(cidade);

        if (json != null) {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonArray days = root.getAsJsonArray("days");

            if (days.size() > 0) {
                JsonObject hoje = days.get(0).getAsJsonObject();

                Clima clima = new Clima();
                clima.setTempAtual(hoje.get("temp").getAsDouble());
                clima.setTempMax(hoje.get("tempmax").getAsDouble());
                clima.setTempMin(hoje.get("tempmin").getAsDouble());
                clima.setUmidade(hoje.get("humidity").getAsDouble());
                clima.setCondicao(hoje.get("conditions").getAsString());
                clima.setPrecipitacao(hoje.has("precip") ? hoje.get("precip").getAsDouble() : 0.0);
                clima.setVentoVel(hoje.get("windspeed").getAsDouble());
                clima.setVentoDir(hoje.get("winddir").getAsDouble());

                String dataOriginal = hoje.get("datetime").getAsString();
                LocalDate data = LocalDate.parse(dataOriginal);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatada = data.format(formatter);
                clima.setData(dataFormatada);

                return clima;
            }
        }
        return null;
    }
}
