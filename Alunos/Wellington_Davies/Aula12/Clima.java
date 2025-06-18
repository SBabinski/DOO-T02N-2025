public class Clima {
    private double temperaturaAtual;
    private double tempMax;
    private double tempMin;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double velocidadeVento;
    private double direcaoVento;

    public Clima(double temperaturaAtual, double tempMax, double tempMin, double umidade, String condicao,
                 double precipitacao, double velocidadeVento, double direcaoVento) {
        this.temperaturaAtual = temperaturaAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public double getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(double precipitacao) {
        this.precipitacao = precipitacao;
    }

    public double getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(double velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public double getDirecaoVento() {
        return direcaoVento;
    }

    public void setDirecaoVento(double direcaoVento) {
        this.direcaoVento = direcaoVento;
    }

    @Override
    public String toString() {
        return "Clima{" +
                "temperaturaAtual=" + temperaturaAtual +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", umidade=" + umidade +
                ", condicao='" + condicao + '\'' +
                ", precipitacao=" + precipitacao +
                ", velocidadeVento=" + velocidadeVento +
                ", direcaoVento=" + direcaoVento +
                '}';
    }
}
