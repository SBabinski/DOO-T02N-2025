package prova2tri;

import java.util.*;

public class SerieManager {
    public static void exibirLista(List<Serie> lista) {
        for (Serie s : lista) {
            System.out.println("\n--- " + s.getNome() + " ---");
            System.out.println("Idioma: " + s.getIdioma());
            System.out.println("Nota: " + s.getNota());
            System.out.println("Status: " + s.getStatus());
            System.out.println("Estreia: " + s.getDataEstreia());
            System.out.println("Fim: " + s.getDataFim());
            System.out.println("Emissora: " + s.getEmissora());
            System.out.println("Gêneros: " + String.join(", ", s.getGeneros()));
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

    public static void ordenarPorData(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getDataEstreia));
    }
}
