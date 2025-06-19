package model;

import java.util.ArrayList;
import java.util.List;

public class DadosApp {
    private List<Series> favoritos = new ArrayList<>();
    private List<Series> assistidas = new ArrayList<>();
    private List<Series> desejaAssistir = new ArrayList<>();
    private List<String> nomesUsuarios = new ArrayList<>();

    public List<Series> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Series> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Series> getAssistidas() {
        return assistidas;
    }

    public void setAssistidas(List<Series> assistidas) {
        this.assistidas = assistidas;
    }

    public List<Series> getDesejaAssistir() {
        return desejaAssistir;
    }

    public void setDesejaAssistir(List<Series> desejaAssistir) {
        this.desejaAssistir = desejaAssistir;
    }

    public List<String> getNomesUsuarios() {
        return nomesUsuarios;
    }

    public void setNomesUsuarios(List<String> nomesUsuarios) {
        this.nomesUsuarios = nomesUsuarios;
    }
}