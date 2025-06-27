package org.series;

import java.util.List;
import java.util.ArrayList;

public class Usuario {

    private String nome;
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejoAssistir = new ArrayList<>();

    public Usuario(String nome, List<Serie> favoritas, List<Serie> assistidas, List<Serie> desejoAssistir) {
        this.nome = nome;
        this.favoritas = favoritas;
        this.assistidas = assistidas;
        this.desejoAssistir = desejoAssistir;
    }

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

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }
}
