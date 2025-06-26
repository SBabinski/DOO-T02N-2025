import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class SeriesManager {
    private final Map<String, List<Show>> listas = new HashMap<>();
    private final String arquivo = "series.json";

    public SeriesManager() {
        listas.put("favoritos", new ArrayList<>());
        listas.put("assistidas", new ArrayList<>());
        listas.put("deseja", new ArrayList<>());

        if (!carregarDoArquivo()) {
            preCarregarSeries(); // só se não houver arquivo
        }
    }

    private void preCarregarSeries() {
        Gson gson = new Gson();

        listas.get("favoritos").add(gson.fromJson("""
        {
          "id": 1,
          "name": "Breaking Bad",
          "language": "English",
          "genres": ["Drama", "Crime", "Thriller"],
          "summary": "<p>Um professor de química se torna traficante.</p>",
          "rating": {"average": 9.5},
          "status": "Ended",
          "premiered": "2008-01-20",
          "ended": "2013-09-29",
          "network": {"name": "AMC"}
        }
    """, Show.class));

        listas.get("favoritos").add(gson.fromJson("""
        {
          "id": 2,
          "name": "Game of Thrones",
          "language": "English",
          "genres": ["Drama", "Fantasy", "Adventure"],
          "summary": "<p>Conflitos épicos em Westeros.</p>",
          "rating": {"average": 9.3},
          "status": "Ended",
          "premiered": "2011-04-17",
          "ended": "2019-05-19",
          "network": {"name": "HBO"}
        }
    """, Show.class));

        listas.get("assistidas").add(gson.fromJson("""
        {
          "id": 3,
          "name": "Stranger Things",
          "language": "English",
          "genres": ["Drama", "Fantasy", "Horror"],
          "summary": "<p>Coisas estranhas acontecem em Hawkins.</p>",
          "rating": {"average": 8.7},
          "status": "Running",
          "premiered": "2016-07-15",
          "ended": null,
          "network": {"name": "Netflix"}
          "network": {"name": "Netflix"}
        }
    """, Show.class));

        listas.get("assistidas").add(gson.fromJson("""
        {
          "id": 4,
          "name": "The Office",
          "language": "English",
          "genres": ["Comedy"],
          "summary": "<p>O cotidiano de um escritório da Dunder Mifflin.</p>",
          "rating": {"average": 8.9},
          "status": "Ended",
          "premiered": "2005-03-24",
          "ended": "2013-05-16",
          "network": {"name": "NBC"}
        }
    """, Show.class));

        listas.get("deseja").add(gson.fromJson("""
        {
          "id": 5,
          "name": "The Boys",
          "language": "English",
          "genres": ["Action", "Drama", "Sci-Fi"],
          "summary": "<p>Super-heróis fora de controle enfrentam justiça brutal.</p>",
          "rating": {"average": 8.6},
          "status": "Running",
          "premiered": "2019-07-26",
          "ended": null,
          "network": {"name": "Amazon Prime Video"}
        }
    """, Show.class));

        listas.get("deseja").add(gson.fromJson("""
        {
          "id": 6,
          "name": "Sherlock",
          "language": "English",
          "genres": ["Crime", "Drama", "Mystery"],
          "summary": "<p>Sherlock Holmes nos tempos modernos.</p>",
          "rating": {"average": 9.1},
          "status": "Ended",
          "premiered": "2010-07-25",
          "ended": "2017-01-15",
          "network": {"name": "BBC"}
        }
    """, Show.class));

        listas.get("deseja").add(gson.fromJson("""
        {
          "id": 7,
          "name": "Dark",
          "language": "German",
          "genres": ["Sci-Fi", "Thriller", "Drama"],
          "summary": "<p>Viagem no tempo e mistérios em uma cidade alemã.</p>",
          "rating": {"average": 8.8},
          "status": "Ended",
          "premiered": "2017-12-01",
          "ended": "2020-06-27",
          "network": {"name": "Netflix"}
        }
    """, Show.class));


        salvarListas();
    }

    private void salvarListas() {
        try (Writer writer = new FileWriter("listas.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void adicionar(String lista, Show show) {
        List<Show> l = listas.get(lista);
        if (l != null && !l.contains(show)) {
            l.add(show);
            salvarEmArquivo();
            System.out.println("Série '" + show.name + "' adicionada à lista de " + lista + ".");
        } else {
            System.out.println("A série já está na lista de " + lista + ".");
        }
    }

    public void removerPorIndice(String lista, int indice) {
        List<Show> l = listas.get(lista);
        if (l != null && indice >= 0 && indice < l.size()) {
            Show removida = l.remove(indice);
            salvarEmArquivo();
            System.out.println("Série '" + removida.name + "' removida da lista de " + lista + ".");
        } else {
            System.out.println("Índice inválido ou lista não existe.");
        }
    }

    public void ordenarLista(String lista, int criterio) {
        List<Show> l = listas.get(lista);
        if (l == null || l.isEmpty()) {
            System.out.println("\nLista vazia ou não existe.\n");
            return;
        }

        switch (criterio) {
            case 1 -> {
                l.sort(Comparator.comparing(s -> s.name.toLowerCase()));
                System.out.println("\nLista ordenada por nome (A-Z):");
            }
            case 2 -> {
                l.sort((s1, s2) -> Double.compare(
                        s2.rating != null && s2.rating.average != null ? s2.rating.average : 0,
                        s1.rating != null && s1.rating.average != null ? s1.rating.average : 0
                ));
                System.out.println("\nLista ordenada por nota (maior primeiro):");
            }
            case 3 -> {
                Map<String, Integer> estado = Map.of(
                        "Ended", 1,       // Concluída
                        "Running", 2,      // Em transmissão
                        "Canceled", 3      // Cancelada
                );
                l.sort(Comparator.comparingInt(s -> estado.getOrDefault(s.status, 99)));
                System.out.println("\nLista ordenada por estado (Concluída > Em transmissão > Cancelada):");
            }
            case 4 -> {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                l.sort((s1, s2) -> {
                    try {
                        LocalDate d1 = s1.premiered != null ? LocalDate.parse(s1.premiered, fmt) : LocalDate.MIN;
                        LocalDate d2 = s2.premiered != null ? LocalDate.parse(s2.premiered, fmt) : LocalDate.MIN;
                        return d1.compareTo(d2);
                    } catch (Exception e) {
                        return 0;
                    }
                });
                System.out.println("\nLista ordenada por data de estreia (mais antiga primeiro):");
            }
        }

        salvarEmArquivo();
        mostrarListaEspecifica(lista); // Esta é a única chamada para mostrar a lista
    }

    public void mostrarListas() {
        for (String lista : listas.keySet()) {
            System.out.println("Lista: " + lista);
            List<Show> series = listas.get(lista);
            if (series.isEmpty()) {
                System.out.println("  (vazia)");
            } else {
                for (int i = 0; i < series.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + series.get(i).name);
                }
            }
        }
    }

    public void mostrarListaEspecifica(String lista) {
        List<Show> l = listas.get(lista);
        if (l == null || l.isEmpty()) {
            System.out.println("\nLista vazia ou não existe.\n");
            return;
        }
        for (int i = 0; i < l.size(); i++) {
            Show serie = l.get(i);
            String statusFormatado = "";
            if (serie.status != null) {
                statusFormatado = switch (serie.status) {
                    case "Ended" -> "(Concluída)";
                    case "Running" -> "(Em transmissão)";
                    case "Canceled" -> "(Cancelada)";
                    default -> "(" + serie.status + ")";
                };
            }
            System.out.printf("\n%d. %s %s", (i + 1), serie.name, statusFormatado);
        }
        System.out.println("\n"); // Adiciona linha vazia no final
    }

    private void salvarEmArquivo() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(arquivo))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listas, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private boolean carregarDoArquivo() {
        Path path = Paths.get(arquivo);
        if (!Files.exists(path)) return false;

        try (Reader reader = Files.newBufferedReader(path)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<Show>>>() {}.getType();
            Map<String, List<Show>> lidas = gson.fromJson(reader, type);

            listas.clear();
            listas.putAll(lidas);
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            return false;
        }
    }

    public List<Show> getLista(String nome) {
        return listas.getOrDefault(nome, new ArrayList<>());
    }



}

