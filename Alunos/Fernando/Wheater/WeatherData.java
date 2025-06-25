package Wheater;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    @JsonProperty("address")
    private String address;
    
    @JsonProperty("currentConditions")
    private CurrentConditions currentConditions;
    
    @JsonProperty("days")
    private List<Day> days;
    
    public WeatherData() {}

    public String getAddress() { 
        return address; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }
    
    public CurrentConditions getCurrentConditions() { 
        return currentConditions; 
    }
    
    public void setCurrentConditions(CurrentConditions currentConditions) { 
        this.currentConditions = currentConditions; 
    }
    
    public List<Day> getDays() { 
        return days; 
    }
    
    public void setDays(List<Day> days) { 
        this.days = days; 
    }
}