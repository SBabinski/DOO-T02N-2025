package Wheater;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    @JsonProperty("tempmax")
    private double maxTemperature;
    
    @JsonProperty("tempmin")
    private double minTemperature;
    
    @JsonProperty("humidity")
    private double humidity;
    
    @JsonProperty("conditions")
    private String conditions;
    
    @JsonProperty("precip")
    private double precipitation;
    
    @JsonProperty("windspeed")
    private double windSpeed;
    
    @JsonProperty("winddir")
    private double windDirection;
    
    public Day() {
        
    }
    
    public double getMaxTemperature() { 
        return maxTemperature; 
    }
    
    public void setMaxTemperature(double maxTemperature) { 
        this.maxTemperature = maxTemperature; 
    }
    
    public double getMinTemperature() { 
        return minTemperature; 
    }
    
    public void setMinTemperature(double minTemperature) { 
        this.minTemperature = minTemperature; 
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
    
    public double getWindDirection() { 
        return windDirection; 
    }
    
    public void setWindDirection(double windDirection) { 
        this.windDirection = windDirection; 
    }
}