package sistemaSerie;

import java.util.Comparator;
import java.util.List;

public class ListaSeries {

    public static void exibirLista(List<Serie> lista) {
        if (lista.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }
        for (Serie serie : lista) {
            System.out.println(serie);
        }
    }

    public static void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
    }

    public static void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNota).reversed());
    }

    public static void ordenarPorStatus(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public static void ordenarPorDataEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getDataEstreia));
    }
}
