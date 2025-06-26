public class App {
    public static void main(String[] args) {
        String cidade = "Matelândia, Brazil";

        try {
            WeatherData clima = WeatherConnector.buscarClima(cidade);

            System.out.println("Clima atual para: " + cidade);
            System.out.println("Temperatura atual: " + clima.getTemperaturaAtual() + "°C");
            System.out.println("Temperatura máxima: " + clima.getTemperaturaMax() + "°C");
            System.out.println("Temperatura mínima: " + clima.getTemperaturaMin() + "°C");
            System.out.println("Umidade: " + clima.getUmidade() + "%");
            System.out.println("Condição: " + clima.getCondicao());
            System.out.println("Precipitação: " + clima.getPrecipitacao() + " mm");
            System.out.println("Vento: " + clima.getVelocidadeVento() + " km/h, direção " + clima.getDirecaoVento() + "°");

        } catch (Exception e) {
            System.err.println("Erro ao buscar clima: " + e.getMessage());
        }
    }
}
