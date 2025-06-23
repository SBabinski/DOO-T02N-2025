import java.util.*;

public class GerenciadorSeries {
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejadas = new ArrayList<>();

    public void adicionar(Serie s, String lista) {
        getLista(lista).add(s);
    }

    public void remover(Serie s, String lista) {
        getLista(lista).removeIf(serie -> serie.getId() == s.getId());
    }

    public List<Serie> getLista(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "favoritas" -> favoritas;
            case "assistidas" -> assistidas;
            case "desejadas" -> desejadas;
            default -> throw new IllegalArgumentException("Lista inválida");
        };
    }

    public void exibirLista(String tipo, Comparator<Serie> ordenacao) {
        List<Serie> lista = new ArrayList<>(getLista(tipo));
        lista.sort(ordenacao);
        lista.forEach(System.out::println);
    }
}
