package org.example;

public class WeatherData {
    private String local;
    private double tempAtual;
    private double tempMax;
    private double tempMin;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double ventoVelocidade;
    private double ventoDirecao;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public double getTempAtual() {
        return tempAtual;
    }

    public void setTempAtual(double tempAtual) {
        this.tempAtual = tempAtual;
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

    public double getVentoVelocidade() {
        return ventoVelocidade;
    }

    public void setVentoVelocidade(double ventoVelocidade) {
        this.ventoVelocidade = ventoVelocidade;
    }

    public double getVentoDirecao() {
        return ventoDirecao;
    }

    public void setVentoDirecao(double ventoDirecao) {
        this.ventoDirecao = ventoDirecao;
    }

    @Override
    public String toString() {
        return String.format("""
                Clima em %s:
                Temperatura atual: %.1f°C
                Máxima do dia: %.1f°C
                Mínima do dia: %.1f°C
                Umidade: %.0f%%
                Condição: %s
                Precipitação: %.1f mm
                Vento: %.1f km/h, direção %.0f°
                """, local, tempAtual, tempMax, tempMin, umidade, condicao, precipitacao, ventoVelocidade, ventoDirecao);
    }
}
