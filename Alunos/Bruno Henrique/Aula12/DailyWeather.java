import com.google.gson.annotations.SerializedName;

public class DailyWeather {
    @SerializedName("tempmax")
    private double tempMax;
    @SerializedName("tempmin")
    private double tempMin;

    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
}