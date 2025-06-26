package util;

import model.Serie;
import java.util.Comparator;
import java.util.List;

public class OrdenadorSeries {
    public static void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
    }

    public static void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
    }

    public static void ordenarPorStatus(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public static void ordenarPorEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(String::compareTo)));
    }
}
