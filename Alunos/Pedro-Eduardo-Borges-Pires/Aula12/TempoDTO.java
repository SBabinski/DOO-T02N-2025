package spring_api_clima_tempo.demo;

import java.util.List;

public class TempoDTO {
	
	
    public List<TempoDia> days;

    public static class TempoDia {
        
    	public String datetime;
        public double temp;
        public double tempmin;
        public double tempmax;
        public double humidity;
        public String conditions;
        public double precip;
        public double windspeed;
        public double winddir;
    }
}