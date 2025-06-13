package Trabalho2;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;
    private List<Noticia> lidas = new ArrayList<>();
    private List<Noticia> paraLerDepois = new ArrayList<>();
    private List<Noticia> favoritas = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Noticia> getLidas() {
        return lidas;
    }

    public List<Noticia> getParaLerDepois() {
        return paraLerDepois;
    }

    public List<Noticia> getFavoritas() {
        return favoritas;
    }
    
}