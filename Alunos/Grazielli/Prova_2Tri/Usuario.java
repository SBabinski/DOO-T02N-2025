package prova2tri;

import java.util.*;

public class Usuario {
    private String nome;
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> queroAssistir = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getQueroAssistir() {
        return queroAssistir;
    }
}
