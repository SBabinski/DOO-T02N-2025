public class ClasseTempo {
    public double temperature;
    public double tempMax;
    public double tempMin;
    public int humidity;
    public String condition;
    public double precipitation;
    public double windSpeed;
    public String windDirection;

    @Override
    public String toString() {
        return String.format(
            "Temperatura atual: %.2f°C\nMáxima do dia: %.2f°C\nMínima do dia: %.2f°C\n" +
            "Umidade: %d%%\nCondição: %s\nPrecipitação: %.2f mm\n" +
            "Vento: %.2f km/h (%s)",
            temperature, tempMax, tempMin, humidity, condition, precipitation, windSpeed, windDirection
        );
    }
}
