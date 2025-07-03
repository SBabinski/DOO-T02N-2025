package clima;

public class Clima {
    private String cidade;
    private double temperaturaAtual;
    private double temperaturaMax;
    private double temperaturaMin;
    private double umidade; // Alterado para double
    private String condicaoTempo;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;

    public Clima(String cidade, double temperaturaAtual, double temperaturaMax, double temperaturaMin,
                 double umidade, String condicaoTempo, double precipitacao,
                 double velocidadeVento, String direcaoVento) {
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.umidade = umidade;
        this.condicaoTempo = condicaoTempo;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public void exibir() {
        System.out.println("===============================");
        System.out.println("Cidade: " + cidade);
        System.out.printf("Temperatura atual: %.1f °C%n", temperaturaAtual);
        System.out.printf("Temperatura máxima: %.1f °C%n", temperaturaMax);
        System.out.printf("Temperatura mínima: %.1f °C%n", temperaturaMin);
        System.out.printf("Umidade: %.1f%%%n", umidade);
        System.out.println("Condição do tempo: " + condicaoTempo);
        System.out.printf("Precipitação: %.1f mm%n", precipitacao);
        System.out.printf("Vento: %.1f km/h (%s)%n", velocidadeVento, direcaoVento);
        System.out.println("===============================");
    }
}
