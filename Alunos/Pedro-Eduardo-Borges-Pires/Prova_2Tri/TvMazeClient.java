package spring_boot_api.tvmaze_api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TvMazeClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public ShowsDTO buscarPrimeiraSeriePorNome(String nome) {
       
    	String url = "https://api.tvmaze.com/search/shows?q=" + nome;

        ResponseEntity<TvMazeResponse[]> response = restTemplate.getForEntity(url, TvMazeResponse[].class);
        
        TvMazeResponse[] resultados = response.getBody();

        if (resultados != null && resultados.length > 0) {
            TvMazeResponse.Show show = resultados[0].getShow();

            ShowsDTO serie = new ShowsDTO();
            
            serie.setNome(show.getName());
            serie.setIdioma(show.getLanguage());
            serie.setGeneros(show.getGenres());
            serie.setNotaGeral(show.getRating().getAverage());
            serie.setEstado(show.getStatus());
            serie.setDataEstreia(show.getPremiered());
            serie.setDataTermino(show.getEnded());
            serie.setEmissora(show.getNetwork() != null ? show.getNetwork().getName() : "Desconhecida");

            return serie;
        }

        return null;
    }
}