import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DesejaAssistir implements IGerenciadorDeSeries {

    private List<Series> lista;

    public DesejaAssistir(List<Series> listaExistente) {
        this.lista = listaExistente;
    }

    @Override
    public void adicionar(Series serie) {
        if (!lista.contains(serie)) {
            lista.add(serie);
        } else {
            System.out.println("A série '" + serie.getName() + "' já está na lista de 'deseja assistir'.");
        }
    }

    @Override
    public void remover(int idSerie) {
        boolean removed = lista.removeIf(serie -> serie.getId() == idSerie);
        if (removed) {
            System.out.println("Série com ID " + idSerie + " removida da lista de 'deseja assistir'.");
        } else {
            System.out.println("Série com ID " + idSerie + " não encontrada na lista de 'deseja assistir'.");
        }
    }

    @Override
    public void listar() {
        listarOrdenado(1);
    }

    public void listarOrdenado(int criterio) {
        List<Series> ordenada = new ArrayList<>(lista);

        switch (criterio) {
            case 1:
                ordenada.sort(Comparator.comparing(Series::getName));
                break;
            case 2:
                ordenada.sort(Comparator.comparing((Series s) -> {
                    Series.Rating rating = s.getRating();
                    return (rating != null && rating.getAverage() != null) ? rating.getAverage() : 0.0;
                }).reversed());
                break;
            case 3:
                ordenada.sort(Comparator.comparing(Series::getStatus, Comparator.nullsLast(String::compareTo)));
                break;
            case 4:
                ordenada.sort(Comparator.comparing(Series::getPremiered, Comparator.nullsLast(String::compareTo)));
                break;
            default:
                System.out.println("Critério de ordenação inválido. Listando por nome.");
                ordenada.sort(Comparator.comparing(Series::getName));
                break;
        }

        if (ordenada.isEmpty()) {
            System.out.println("Nenhuma série na lista de 'deseja assistir'.");
            return;
        }

        System.out.println("\n--- Séries Que Deseja Assistir (" + getCriterionName(criterio) + ") ---");
        ordenada.forEach(serie -> System.out.println(
                "Id: " + serie.getId() +
                        " | Nome: " + serie.getName() +
                        " | Idioma: " + serie.getLanguage() +
                        " | Gêneros: " + (serie.getGenres() != null && !serie.getGenres().isEmpty() ? String.join(", ", serie.getGenres()) : "N/A") +
                        " | Nota: " + (serie.getRating() != null && serie.getRating().getAverage() != null ? serie.getRating().getAverage() : "N/A") +
                        " | Status: " + (serie.getStatus() != null ? serie.getStatus() : "Desconhecido") +
                        " | Estreia: " + (serie.getPremiered() != null ? serie.getPremiered() : "Desconhecida") +
                        " | Emissora: " + (serie.getNetwork() != null ? serie.getNetwork().getName() : "Desconhecida")
        ));
    }

    private String getCriterionName(int criterio) {
        switch (criterio) {
            case 1:
                return "Nome (A-Z)";
            case 2:
                return "Nota geral (maior para menor)";
            case 3:
                return "Estado da série";
            case 4:
                return "Data de estreia";
            default:
                return "Desconhecido";
        }
    }

    @Override
    public List<Series> getLista() {
        return lista;
    }
}