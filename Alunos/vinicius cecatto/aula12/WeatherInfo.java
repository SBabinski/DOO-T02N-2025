package aula12;

public class WeatherInfo {
    private final String cidade;
    private final double temperaturaAtual;
    private final double temperaturaMax;
    private final double temperaturaMin;
    private final double umidade;
    private final String condicao;
    private final double precipitacao;
    private final double velocidadeVento;
    private final double direcaoVento;
    private final String address; // New field to store the full address from the API

    public WeatherInfo(String cidade, double temperaturaAtual, double temperaturaMax, double temperaturaMin,
                       double umidade, String condicao, double precipitacao, double velocidadeVento, double direcaoVento, String address) {
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
        this.address = address; // Initialize the new field
    }

    // Getter for the address
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return String.format("""
                Clima para %s:
                - Localização: %s
                - Temperatura atual: %.1f°C
                - Máxima do dia: %.1f°C
                - Mínima do dia: %.1f°C
                - Umidade do ar: %.1f%%
                - Condição: %s
                - Precipitação: %.1f mm
                - Vento: %.1f km/h, Direção: %.0f°
                """, cidade, address, temperaturaAtual, temperaturaMax, temperaturaMin, umidade, condicao, precipitacao, velocidadeVento, direcaoVento);
    }
}