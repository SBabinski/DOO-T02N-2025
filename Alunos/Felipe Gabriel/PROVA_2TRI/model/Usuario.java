package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> assistidas = new ArrayList<>();
    private List<Serie> desejo = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

   
    public String getNome() { return nome; }
    public List<Serie> getFavoritas() { return favoritas; }
    public List<Serie> getAssistidas() { return assistidas; }
    public List<Serie> getDesejo() { return desejo; }

  
    public void adicionarFavorita(Serie s) { favoritas.add(s); }
    public void removerFavorita(int id) { favoritas.removeIf(s -> s.getId() == id); }

    public void adicionarAssistida(Serie s) { assistidas.add(s); }
    public void removerAssistida(int id) { assistidas.removeIf(s -> s.getId() == id); }

    public void adicionarDesejo(Serie s) { desejo.add(s); }
    public void removerDesejo(int id) { desejo.removeIf(s -> s.getId() == id); }
}
