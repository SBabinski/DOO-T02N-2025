import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListaDeSeries {
    private List<Serie> series;

    public ListaDeSeries() {
        this.series = new ArrayList<>();
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public void adicionarSerie(Serie serie) {
        this.series.add(serie);
    }

    public void removerSerie(Serie serie) {
        this.series.remove(serie);
    }

    public void salvarJson(String caminhoArquivo) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            gson.toJson(this.series, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a lista em JSON: " + e.getMessage());
        }
    }

    public void carregarJson(String caminhoArquivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try (FileReader reader = new FileReader(caminhoArquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Serie>>() {}.getType();
            this.series = gson.fromJson(reader, tipoLista);

            if (this.series == null) {
                this.series = new ArrayList<>();
            }

        } catch (Exception e) {
            this.series = new ArrayList<>();
        }
    }

    public List<Serie> listaOrdenadaPorNome() {
        return this.series.stream()
                .sorted(Comparator.comparing(Serie::getNome))
                .collect(Collectors.toList());
    }

    public List<Serie> listaOrdenadaPorNota() {
        return this.series.stream()
                .sorted(Comparator.comparing(Serie::getNota).reversed())
                .collect(Collectors.toList());
    }

    public List<Serie> listaOrdenadaPorEstado() {
        return this.series.stream()
                .sorted(Comparator.comparing(Serie::getEstado))
                .collect(Collectors.toList());
    }

    public List<Serie> listaOrdenadaPorDataDeEstreia() {
        return this.series.stream()
                .sorted(Comparator.comparing(Serie::getDataDeLancamento))
                .collect(Collectors.toList());
    }
}