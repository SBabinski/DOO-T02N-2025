import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorClima {
    public Clima converterJsonParaClima(String json) {
        JsonObject objeto = JsonParser.parseString(json).getAsJsonObject();
        JsonArray dias = objeto.getAsJsonArray("days");
        JsonObject hoje = dias.get(0).getAsJsonObject();

        Clima clima = new Clima();
        clima.setData(hoje.get("datetime").getAsString());
        clima.setTemperaturaAtual(hoje.get("temp").getAsDouble());
        clima.setTemperaturaMaxima(hoje.get("tempmax").getAsDouble());
        clima.setTemperaturaMinima(hoje.get("tempmin").getAsDouble());
        clima.setUmidade(hoje.get("humidity").getAsDouble());
        clima.setCondicaoTempo(hoje.get("conditions").getAsString());
        clima.setPrecipitacao(hoje.get("precip").getAsDouble());
        clima.setVelocidadeVento(hoje.get("windspeed").getAsDouble());
        clima.setDirecaoVento(hoje.get("winddir").getAsDouble());

        return clima;
    }
}
