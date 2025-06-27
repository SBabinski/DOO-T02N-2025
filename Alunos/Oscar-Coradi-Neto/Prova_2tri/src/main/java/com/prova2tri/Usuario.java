package com.prova2tri;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> paraAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.paraAssistir = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public List<Serie> getFavoritas() { return favoritas; }
    public List<Serie> getAssistidas() { return assistidas; }
    public List<Serie> getParaAssistir() { return paraAssistir; }

    public void adicionarSerie(Serie serie, List<Serie> lista) {
        if (!lista.contains(serie)) {
            lista.add(serie);
            System.out.println("'" + serie.getNome() + "' adicionada à lista com sucesso!");
        } else {
            System.out.println("'" + serie.getNome() + "' já está nesta lista.");
        }
    }

    public void removerSerie(Serie serie, List<Serie> lista) {
        if (lista.remove(serie)) {
            System.out.println("'" + serie.getNome() + "' removida da lista com sucesso!");
        } else {
            System.out.println("'" + serie.getNome() + "' não encontrada nesta lista.");
        }
    }

    public void exibirLista(String nomeLista, List<Serie> lista) {
        System.out.println("\n--- LISTA: " + nomeLista.toUpperCase() + " ---");
        if (lista.isEmpty()) {
            System.out.println("Esta lista está vazia.");
        } else {
            lista.forEach(System.out::println);
        }
        System.out.println("--- FIM DA LISTA ---");
    }

    public void ordenarListaPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
    }

    public void ordenarListaPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
    }

    public void ordenarListaPorStatus(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public void ordenarListaPorDataEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getDataEstreia, Comparator.nullsLast(Comparator.naturalOrder())));
    }
}