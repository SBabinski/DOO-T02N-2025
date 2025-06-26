public class WeatherData {
    private final double temperaturaAtual;
    private final double temperaturaMax;
    private final double temperaturaMin;
    private final int umidade;
    private final String condicao;
    private final double precipitacao;
    private final double velocidadeVento;
    private final double direcaoVento;

    public WeatherData(double temperaturaAtual, double temperaturaMax, double temperaturaMin,
                       int umidade, String condicao, double precipitacao,
                       double velocidadeVento, double direcaoVento) {
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public double getTemperaturaAtual() { return temperaturaAtual; }
    public double getTemperaturaMax() { return temperaturaMax; }
    public double getTemperaturaMin() { return temperaturaMin; }
    public int getUmidade() { return umidade; }
    public String getCondicao() { return condicao; }
    public double getPrecipitacao() { return precipitacao; }
    public double getVelocidadeVento() { return velocidadeVento; }
    public double getDirecaoVento() { return direcaoVento; }
}
