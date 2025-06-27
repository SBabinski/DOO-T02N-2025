package spring_api_clima_tempo.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TempoService {

    private String key = "P73EEUKRZUD46M53JXTWKDP9U";
    private String urlServico = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public TempoResponse.TempoDia getTempoDoLocal(String cidade) {
        RestTemplate restTemplate = new RestTemplate();
        String url = urlServico + cidade + "/today?unitGroup=metric&key=" + key + "&include=days";

        TempoResponse response = restTemplate.getForObject(url, TempoResponse.class);
        
        if(response != null && !response.days.isEmpty())
        	return response.days.get(0);
        else
        	return null;
       
    }
}
