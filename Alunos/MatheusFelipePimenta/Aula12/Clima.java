package Aula12;

public class Clima {
    public String cidade;
    public double temperaturaAtual;
    public double temperaturaMaxima;
    public double temperaturaMinima;
    public double umidade;
    public String condicaoTempo;
    public double precipitacao;
    public double velocidadeVento;
    public String direcaoVento;

    @Override
    public String toString() {
        return "Clima em " + cidade + ":\n" +
               "Temperatura atual: " + temperaturaAtual + "°C\n" +
               "Máxima: " + temperaturaMaxima + "°C\n" +
               "Mínima: " + temperaturaMinima + "°C\n" +
               "Umidade: " + umidade + "%\n" +
               "Condição: " + condicaoTempo + "\n" +
               "Precipitação: " + precipitacao + "mm\n" +
               "Vento: " + velocidadeVento + "km/h, " + direcaoVento + "\n";
    }
}
