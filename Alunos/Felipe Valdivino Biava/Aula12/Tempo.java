public class Tempo {
    private String date;
    private double temp;
    private double tempMax;
    private double tempMin;
    private double humidity;
    private String conditions;
    private double precipitation;
    private double windSpeed;
    private String windDirection;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
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

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public String toString() {
        return "Data: " + date + "\n" +
                "Temperatura atual: " + temp + " °C\n" +
                "Máxima: " + tempMax + " °C\n" +
                "Mínima: " + tempMin + " °C\n" +
                "Umidade: " + humidity + " %\n" +
                "Condições: " + conditions + "\n" +
                "Precipitação: " + precipitation + " mm\n" +
                "Vento: " + windSpeed + " km/h, direção " + windDirection;
    }
}
