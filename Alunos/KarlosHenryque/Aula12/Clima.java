public class Clima {
    private double tempAtual;
    private double tempMax;
    private double tempMin;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double ventoVel;
    private double ventoDir;
    private String data;

    public double getTempAtual() {
        return tempAtual;
    }

    public void setTempAtual(double tempAtual) {
        this.tempAtual = tempAtual;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
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

    public double getVentoVel() {
        return ventoVel;
    }

    public void setVentoVel(double ventoVel) {
        this.ventoVel = ventoVel;
    }

    public double getVentoDir() {
        return ventoDir;
    }

    public void setVentoDir(double ventoDir) {
        this.ventoDir = ventoDir;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {

        return "===== Clima Atual =====\n" +
                "Data: " + data + "\n" +
                "Temperatura Atual: " + tempAtual + "°C\n" +
                "Temperatura Máxima: " + tempMax + "°C\n" +
                "Temperatura Mínima: " + tempMin + "°C\n" +
                "Umidade: " + umidade + "%\n" +
                "Condição do tempo: " + condicao + "\n" +
                "Precipitação: " + precipitacao + " mm\n" +
                "Velocidade do vento: " + ventoVel + " km/h\n" +
                "Direção do vento: " + ventoDir + "°\n";
    }
}