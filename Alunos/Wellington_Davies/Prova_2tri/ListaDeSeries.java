import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListaDeSeries {
    private String tipo; 
    private List<Serie> series; 

    public ListaDeSeries(String tipo, List<Serie> series) {
        this.tipo = tipo;
        this.series = series;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    // Adiciona uma série à lista
    public void adicionarSerie(Serie serie) {
        if (series != null) {
            series.add(serie);
        } else {
            System.out.println("A lista de séries está vazia.");
        }
    }

    // Remove uma série da lista
    public void removerSerie(Serie serie) {
        if (series != null && series.contains(serie)) {
            series.remove(serie);
        } else {
            System.out.println("A série não está na lista.");
        }
    }

    // Retorna a lista de séries ordenada por nome
    public List<Serie> listaOrdenadaPorNome() {
        return series.stream()
                .sorted(Comparator.comparing(Serie::getNome)) // Ordena pelo nome
                .toList();
    }

    // Retorna a lista de séries ordenada por nota
    public List<Serie> listaOrdenadaPorNota() {
        return series.stream()
                .sorted(Comparator.comparing(Serie::getNota).reversed()) // Ordena pela nota
                .toList();
    }

    // Filtra séries pelo estado (ex.: "Running", "Ended")
    public List<Serie> listarSeriesPorEstado(String estado) {
        if (series == null || series.isEmpty()) {
            System.out.println("A lista de séries está vazia.");
            return List.of();
        }
        return series.stream()
                .filter(serie -> serie.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    // Filtra séries pela data de estreia
    public List<Serie> listarPorDataDeEstreia(LocalDate dataDeEstreia) {
        if (series == null || series.isEmpty()) {
            System.out.println("A lista de séries está vazia.");
            return List.of();
        }
        return series.stream()
                .filter(serie -> serie.getDataDeLancamento().isEqual(dataDeEstreia))
                .toList();
    }

    // Filtra séries pela data de término
    public List<Serie> listarPorDataDeTermino(LocalDate dataDeTermino) {
        if (series == null || series.isEmpty()) {
            System.out.println("A lista de séries está vazia.");
            return List.of();
        }
        return series.stream()
                .filter(serie -> serie.getDataDeTermino() != null && serie.getDataDeTermino().isEqual(dataDeTermino))
                .toList();
    }

    // Salva a lista de séries em um arquivo JSON
    public void salvarEmJson(String caminhoArquivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        src == null ? null : new com.google.gson.JsonPrimitive(src.toString())) // Serializa LocalDate como String
                .create();
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            gson.toJson(this.series, writer); // Converte a lista para JSON
            System.out.println("Lista de séries salva em " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a lista em JSON: " + e.getMessage());
        }
    }

    // Carrega a lista de séries de um arquivo JSON
    public void carregarDeJson(String caminhoArquivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        json == null ? null : LocalDate.parse(json.getAsString())) // Desserializa String como LocalDate
                .create();
        try (FileReader reader = new FileReader(caminhoArquivo)) {
            // Verifica se o arquivo está vazio
            if (new java.io.File(caminhoArquivo).length() == 0) {
                System.out.println("O arquivo JSON está vazio. Nenhuma série foi carregada.");
                this.series = new ArrayList<>(); // Inicializa uma lista vazia
                return;
            }

            Type listType = new TypeToken<ArrayList<Serie>>() {}.getType();
            this.series = gson.fromJson(reader, listType); // Converte o JSON para uma lista de objetos Serie
            System.out.println("Lista de séries carregada de " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a lista de JSON: " + e.getMessage());
            this.series = new ArrayList<>(); // Inicializa uma lista vazia em caso de erro
        } catch (com.google.gson.JsonSyntaxException e) {
            System.err.println("Erro de sintaxe no arquivo JSON: " + e.getMessage());
            this.series = new ArrayList<>(); // Inicializa uma lista vazia em caso de erro de sintaxe
        }
    }

    // Limpa o conteúdo do arquivo JSON
    public void limparJson(String caminhoArquivo) {
    try (FileWriter writer = new FileWriter(caminhoArquivo)) {
        writer.write("[]"); // Escreve uma lista vazia no arquivo JSON
        System.out.println("O arquivo JSON foi limpo: " + caminhoArquivo);
        
        // limpa a lista em memória
        this.series.clear();
        System.out.println("Lista em memória também foi limpa.");
        
    } catch (IOException e) {
        System.err.println("Erro ao limpar o arquivo JSON: " + e.getMessage());
    }
}
}
