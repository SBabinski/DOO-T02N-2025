package model;

public class Clima {
    private String cidade;
    private String data;
    private double temperaturaAtual;
    private double tempMax;
    private double tempMin;
    private int umidade;
    private String condicao;
    private double precipitacao;
    private double ventoVelocidade;
    private String ventoDirecao;

  
    public Clima(String cidade, String data, double temperaturaAtual, double tempMax, double tempMin, int umidade, String condicao, double precipitacao, double ventoVelocidade, String ventoDirecao) {
        this.cidade = cidade;
        this.data = data;
        this.temperaturaAtual = temperaturaAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.ventoVelocidade = ventoVelocidade;
        this.ventoDirecao = ventoDirecao;
    }

    @Override
    public String toString() {
        return "\n📍 Cidade: " + cidade +
               "\n📆 Data: " + data +
               "\n🌡️ Temp. Atual: " + temperaturaAtual + "°C" +
               "\n🌡️ Máxima: " + tempMax + "°C | Mínima: " + tempMin + "°C" +
               "\n💧 Umidade: " + umidade + "%" +
               "\n⛅ Condição: " + condicao +
               "\n🌧️ Precipitação: " + precipitacao + " mm" +
               "\n💨 Vento: " + ventoVelocidade + " km/h " + "(" + ventoDirecao + ")";
    }
}
