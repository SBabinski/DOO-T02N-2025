package sistemaTempo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        sistemaTempo.service.ClimaService service = new sistemaTempo.service.ClimaService();

        System.out.print("Digite a cidade para buscar o clima: ");
        String cidade = scanner.nextLine();

        try {
            Clima clima = service.obterClima(cidade);

            String condicaoTraduzida = traduzirCondicao(clima.getCondicao());

            System.out.println("\nInformações do clima:");
            System.out.println("Temperatura Atual: " + clima.getTemperaturaAtual() + "°C");
            System.out.println("Máxima do dia: " + clima.getTemperaturaMax() + "°C");
            System.out.println("Mínima do dia: " + clima.getTemperaturaMin() + "°C");
            System.out.println("Umidade: " + clima.getUmidade() + "%");
            System.out.println("Condição: " + condicaoTraduzida + " " + getEmojiClima(clima.getCondicao()));
            System.out.println("Precipitação: " + clima.getPrecipitacao() + " mm");
            System.out.println("Vento: " + clima.getVelocidadeVento() + " km/h");
            System.out.println("Direção do Vento: " + clima.getDirecaoVento());
        } catch (Exception e) {
            System.out.println("Erro ao obter clima: " + e.getMessage());
        }
    }

    public static String getEmojiClima(String condicao) {
        String c = condicao.toLowerCase();
        if (c.contains("sun") || c.contains("clear")) return "☀️";
        if (c.contains("cloud") && c.contains("part")) return "⛅";
        if (c.contains("cloud") || c.contains("overcast")) return "☁️";
        if (c.contains("rain") || c.contains("shower")) return "🌧️";
        if (c.contains("thunder")) return "⛈️";
        if (c.contains("snow")) return "❄️";
        if (c.contains("fog") || c.contains("mist")) return "🌫️";
        return "🌍";
    }

    public static String traduzirCondicao(String condicaoIngles) {
        String c = condicaoIngles.toLowerCase();
        if (c.contains("sun") || c.contains("clear")) return "Ensolarado";
        if (c.contains("cloud") && c.contains("part")) return "Parcialmente Nublado";
        if (c.contains("cloud") || c.contains("overcast")) return "Nublado";
        if (c.contains("rain") || c.contains("shower")) return "Chuva";
        if (c.contains("thunder")) return "Tempestade";
        if (c.contains("snow")) return "Neve";
        if (c.contains("fog") || c.contains("mist")) return "Neblina";
        return "Desconhecido";
    }
}
