package Prova_2tri;

import java.util.*;

public class Usuario {
    private String nome;
    private Set<Serie> favoritos;
    private Set<Serie> assistidas;
    private Set<Serie> queroAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritos = new HashSet<>();
        this.assistidas = new HashSet<>();
        this.queroAssistir = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public Set<Serie> getFavoritos() {
        return favoritos;
    }

    public Set<Serie> getAssistidas() {
        return assistidas;
    }

    public Set<Serie> getQueroAssistir() {
        return queroAssistir;
    }

    public void addFavorito(Serie serie) {
        favoritos.add(serie);
    }

    public void removeFavorito(Serie serie) {
        favoritos.remove(serie);
    }

    public void addAssistida(Serie serie) {
        assistidas.add(serie);
    }

    public void removeAssistida(Serie serie) {
        assistidas.remove(serie);
    }

    public void addQueroAssistir(Serie serie) {
        queroAssistir.add(serie);
    }

    public void removeQueroAssistir(Serie serie) {
        queroAssistir.remove(serie);
    }
}