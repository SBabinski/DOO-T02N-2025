package Aula12;

public class WeatherInfo {
    private final double currentTemperature;
    private final double maxTemperature;
    private final double minTemperature;
    private final double humidity;
    private final String condition;
    private final double precipitation;
    private final double windSpeed;
    private final String windDirection;

    public WeatherInfo(double currentTemperature, double maxTemperature, double minTemperature,
                       double humidity, String condition, double precipitation,
                       double windSpeed, String windDirection) {
        this.currentTemperature = currentTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.humidity = humidity;
        this.condition = condition;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getCondition() {
        return condition;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }
}