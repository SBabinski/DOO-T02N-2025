package Wheater;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentConditions {
    
    @JsonProperty("temp")
    private double temperature;
    
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
    
    @JsonProperty("datetime")
    private String datetime;
    
    public CurrentConditions() {

    }
    
    public double getTemperature() { 
        return temperature; 
    }
    
    public void setTemperature(double temperature) { 
        this.temperature = temperature; 
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
    
    public String getDatetime() { 
        return datetime; 
    }
    
    public void setDatetime(String datetime) { 
        this.datetime = datetime; 
    }
}