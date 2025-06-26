package com.faculdade.tv_series;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects; 

public class Usuario {
    private String nome;
    private List<Serie> favoritos;
    private List<Serie> assistidas;
    private List<Serie> desejadas;

    public Usuario() {
        this.nome = "Novo Usuário"; 
        this.favoritos = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejadas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejadas() {
        return desejadas;
    }

   
    public void adicionar(List<Serie> lista, Serie serie) {
        
        boolean jaExiste = lista.stream().anyMatch(s -> Objects.equals(s.getId(), serie.getId()));
        if (!jaExiste) {
            lista.add(serie);
            System.out.println("'" + serie.getNome() + "' adicionado à lista.");
        } else {
            System.out.println("'" + serie.getNome() + "' já está na lista.");
        }
    }

    
    public void remover(List<Serie> lista, Serie serie) {
        if (lista.removeIf(s -> Objects.equals(s.getId(), serie.getId()))) {
            System.out.println("'" + serie.getNome() + "' removido da lista.");
        } else {
            System.out.println("'" + serie.getNome() + "' não encontrado na lista.");
        }
    }
}