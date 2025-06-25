package Prova_2tri;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;

    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejadas = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejadas() {
        return desejadas;
    }
    
}