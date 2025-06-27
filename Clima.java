import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Clima {
    private String data;
    private double temperaturaAtual;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private double umidade;
    private String condicaoTempo;
    private double precipitacao;
    private double velocidadeVento;
    private double direcaoVento;


    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public double getTemperaturaAtual() { return temperaturaAtual; }
    public void setTemperaturaAtual(double temperaturaAtual) { this.temperaturaAtual = temperaturaAtual; }

    public double getTemperaturaMaxima() { return temperaturaMaxima; }
    public void setTemperaturaMaxima(double temperaturaMaxima) { this.temperaturaMaxima = temperaturaMaxima; }

    public double getTemperaturaMinima() { return temperaturaMinima; }
    public void setTemperaturaMinima(double temperaturaMinima) { this.temperaturaMinima = temperaturaMinima; }

    public double getUmidade() { return umidade; }
    public void setUmidade(double umidade) { this.umidade = umidade; }

    public String getCondicaoTempo() { return condicaoTempo; }
    public void setCondicaoTempo(String condicaoTempo) { this.condicaoTempo = condicaoTempo; }

    public double getPrecipitacao() { return precipitacao; }
    public void setPrecipitacao(double precipitacao) { this.precipitacao = precipitacao; }

    public double getVelocidadeVento() { return velocidadeVento; }
    public void setVelocidadeVento(double velocidadeVento) { this.velocidadeVento = velocidadeVento; }

    public double getDirecaoVento() { return direcaoVento; }
    public void setDirecaoVento(double direcaoVento) { this.direcaoVento = direcaoVento; }


    private String formatarData() {
        LocalDate dataFormatada = LocalDate.parse(this.data);
        return dataFormatada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    private String traduzirCondicao() {
        String condicao = this.condicaoTempo.toLowerCase();
        switch (condicao) {
            case "clear": return "Céu limpo";
            case "partially cloudy": return "Parcialmente nublado";
            case "overcast": return "Encoberto";
            case "cloudy": return "Nublado";
            case "rain": return "Chuva";
            case "light rain": return "Chuva leve";
            case "heavy rain": return "Chuva forte";
            case "snow": return "Neve";
            case "thunderstorm": return "Tempestade";
            case "fog": return "Nevoeiro";
            default: return this.condicaoTempo;
        }
    }

    @Override
    public String toString() {
        return "\nClima em " + formatarData() + ":\n" +
                "- Temperatura atual: " + temperaturaAtual + "°C\n" +
                "- Máxima: " + temperaturaMaxima + "°C\n" +
                "- Mínima: " + temperaturaMinima + "°C\n" +
                "- Umidade: " + umidade + "%\n" +
                "- Condição do tempo: " + traduzirCondicao() + "\n" +
                "- Precipitação: " + precipitacao + " mm\n" +
                "- Vento: " + velocidadeVento + " km/h, direção " + direcaoVento + "°";
    }
}
