package com.faculdade.tvseries;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritos = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejadas = new ArrayList<>();

    
    public Usuario() {}

    public Usuario(String nome) {
        this.nome = nome;
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
        
        if (!lista.contains(serie)) {
            lista.add(serie);
        } else {
            
            System.out.println("Série '" + serie.getNome() + "' já está nesta lista.");
        }
    }

    public void remover(List<Serie> lista, Serie serie) {
    
        lista.remove(serie);
    }
}