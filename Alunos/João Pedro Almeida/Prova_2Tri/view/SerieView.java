package com.cinelume.view;

import com.cinelume.model.Serie;
import java.util.List;

public class SerieView {
    public void mostrarResultados(List<Serie> series) {
        if (series == null || series.isEmpty()) {
            System.out.println("\nNenhuma série encontrada!");
            return;
        }

        System.out.println("\nSegue aqui os Resultados");
        for (int i = 0; i < series.size(); i++) {
            Serie serie = series.get(i);
            System.out.printf("%d. %s (⭐ %.1f)\n", 
                i + 1, serie.getNome(), serie.getNota());
        }
    }

    public void exibirDetalhesCompletos(Serie serie) {
        if (serie == null) {
            System.out.println("\nSérie não encontrada!");
            return;
        }

        System.out.println("\n=== " + serie.getNome().toUpperCase() + " ===");
        System.out.println("Idioma: " + serie.getIdioma());
        System.out.println("Gêneros: " + String.join(", ", serie.getGeneros()));
        System.out.println("Nota: ⭐ " + serie.getNota());
        System.out.println("Status: " + formatarStatus(serie.getStatus()));
        System.out.println("Estreia: " + (serie.getDataEstreia() != null ? 
            serie.getDataEstreia().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
        System.out.println("Emissora: " + serie.getEmissora());
    }

    public void mostrarLista(List<Serie> series) {
        if (series == null || series.isEmpty()) {
            System.out.println("\nLista vazia!");
            return;
        }

        System.out.println("\nItens na lista (" + series.size() + "):");
        for (Serie serie : series) {
            System.out.printf("- %s (⭐ %.1f, %s)\n", 
                serie.getNome(), 
                serie.getNota(), 
                formatarStatus(serie.getStatus()));
        }
    }

    private String formatarStatus(String status) {
        if (status == null) return "Desconhecido";
        
        return switch (status.toLowerCase()) {
            case "running", "em exibição" -> "Em exibição";
            case "ended", "concluída" -> "Concluída";
            case "canceled", "cancelada" -> "Cancelada";
            default -> status; // Mantém o original se não for um caso conhecido
        };
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println("\n" + mensagem);
    }
}