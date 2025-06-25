package com.cinelume.util;

import com.cinelume.model.Serie;
import java.time.LocalDate;
import java.util.Comparator;

public class SeriesComparators {
    public static Comparator<Serie> porNome() {
        return Comparator.comparing(Serie::getNome, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Serie> porNota() {
        return Comparator.comparingDouble(Serie::getNota)
                        .reversed()
                        .thenComparing(porNome()); // Desempate por nome
    }

    public static Comparator<Serie> porStatus() {
        return Comparator.comparing(Serie::getStatus, 
            Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
    }

    public static Comparator<Serie> porData() {
        return Comparator.comparing(
            Serie::getDataEstreia, 
            Comparator.nullsLast(Comparator.naturalOrder())
        );
    }

    // Novo: Comparador combinado (ex.: por status + data)
    public static Comparator<Serie> porStatusEData() {
        return porStatus().thenComparing(porData());
    }
}