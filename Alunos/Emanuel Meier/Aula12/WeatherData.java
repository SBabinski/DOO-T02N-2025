public class WeatherData {
    private final String local;
    private final double temperaturaAtual;
    private final double temperaturaMax;
    private final double temperaturaMin;
    private final double umidade;
    private final String condicao;
    private final double precipitacao;
    private final double velocidadeVento;
    private final double direcaoVento;

    public WeatherData(String local, double temperaturaAtual, double temperaturaMax, double temperaturaMin,
                       double umidade, String condicao, double precipitacao, double velocidadeVento, double direcaoVento) {
        this.local = local;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    @Override
    public String toString() {
        return String.format("""
            Local: %s
            Temperatura Atual: %.1f°C
            Máxima: %.1f°C
            Mínima: %.1f°C
            Umidade: %.0f%%
            Condição: %s
            Precipitação: %.1f mm
            Vento: %.1f km/h (%.0f°)
            """, local, temperaturaAtual, temperaturaMax, temperaturaMin, umidade,
                condicao, precipitacao, velocidadeVento, direcaoVento);
    }
}
