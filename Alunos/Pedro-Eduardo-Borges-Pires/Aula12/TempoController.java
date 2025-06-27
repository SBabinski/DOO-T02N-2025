package spring_api_clima_tempo.demo;



import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class TempoController {

    private final TempoService tempoService;

    public TempoController (TempoService tempoService) {
        this.tempoService = tempoService;
    }

    @GetMapping("/{cidade}")
    public TempoResponse.TempoDia getWeather(@PathVariable String cidade) {
        return tempoService.getTempoDoLocal(cidade);
    }
}
