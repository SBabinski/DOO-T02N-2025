import com.google.gson.annotations.SerializedName;

public class CurrentConditions {
    @SerializedName("temp")
    private double temperature;
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("conditions")
    private String conditions;
    @SerializedName("precip")
    private double precipitation;
    @SerializedName("windspeed")
    private double windSpeed;
    @SerializedName("winddir")
    private double windDirection;

    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public String getConditions() { return conditions; }
    public double getPrecipitation() { return precipitation; }
    public double getWindSpeed() { return windSpeed; }
    public double getWindDirection() { return windDirection; }
}