import java.util.List;

public class WeatherResponse {
    private String resolvedAddress;
    private List<DailyWeather> days;
    private CurrentConditions currentConditions;

    public String getResolvedAddress() { return resolvedAddress; }
    public List<DailyWeather> getDays() { return days; }
    public CurrentConditions getCurrentConditions() { return currentConditions; }
}