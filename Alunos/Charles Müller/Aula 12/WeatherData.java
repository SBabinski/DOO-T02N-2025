import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa os dados meteorológicos de uma consulta
 *
 * @author Charles Müller
 * @version 1.0
 */
public class WeatherData {
    private String cidade;
    private LocalDateTime dataConsulta;
    private double temperaturaAtual;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private double humidade;
    private String condicaoTempo;
    private double precipitacao;
    private double velocidadeVento;
    private double direcaoVento;

    /**
     * Construtor padrão
     */
    public WeatherData() {
        this.dataConsulta = LocalDateTime.now();
    }

    /**
     * Construtor com parâmetros principais
     */
    public WeatherData(String cidade, double temperaturaAtual, double temperaturaMaxima,
            double temperaturaMinima, double humidade, String condicaoTempo,
            double precipitacao, double velocidadeVento, double direcaoVento) {
        this();
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.humidade = humidade;
        this.condicaoTempo = condicaoTempo;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    // Getters e Setters

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public double getHumidade() {
        return humidade;
    }

    public void setHumidade(double humidade) {
        this.humidade = humidade;
    }

    public String getCondicaoTempo() {
        return condicaoTempo;
    }

    public void setCondicaoTempo(String condicaoTempo) {
        this.condicaoTempo = condicaoTempo;
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

    /**
     * Valida se os dados estão consistentes
     */
    public boolean isValid() {
        return cidade != null && !cidade.trim().isEmpty() &&
                temperaturaMaxima >= temperaturaMinima &&
                humidade >= 0 && humidade <= 100 &&
                precipitacao >= 0 &&
                velocidadeVento >= 0 &&
                direcaoVento >= 0 && direcaoVento <= 360;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "WeatherData{cidade='%s', data='%s', temp=%.1f°C, max=%.1f°C, min=%.1f°C, humidade=%.0f%%, condicao='%s'}",
                cidade, dataConsulta.format(formatter), temperaturaAtual, temperaturaMaxima,
                temperaturaMinima, humidade, condicaoTempo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        WeatherData that = (WeatherData) obj;
        return cidade.equals(that.cidade) &&
                dataConsulta.equals(that.dataConsulta);
    }

    @Override
    public int hashCode() {
        return cidade.hashCode() + dataConsulta.hashCode();
    }
}
