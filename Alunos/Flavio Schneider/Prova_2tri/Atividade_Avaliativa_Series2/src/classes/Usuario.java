package classes;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private String nome;
	private List<Serie> favoritas;
    private List<Serie> assistidas;
    private List<Serie> desejadas;
	
    public Usuario() {
        this.nome = "";
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejadas = new ArrayList<>();
    }

    public Usuario(String nome) {
        this.nome = nome;
        this.favoritas = new ArrayList<>();
        this.assistidas = new ArrayList<>();
        this.desejadas = new ArrayList<>();
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Serie> getFavoritas() {
		return favoritas;
	}

	public void setFavoritas(List<Serie> favoritas) {
		this.favoritas = favoritas;
	}

	public List<Serie> getAssistidas() {
		return assistidas;
	}

	public void setAssistidas(List<Serie> assistidas) {
		this.assistidas = assistidas;
	}

	public List<Serie> getDesejadas() {
		return desejadas;
	}

	public void setDesejadas(List<Serie> desejadas) {
		this.desejadas = desejadas;
	}
    
    
	//metodos q adicionam e removem as series
	public void adicionarFavorita(Serie serie) {
        if (!favoritas.contains(serie)) {
            favoritas.add(serie);
        }
    }

    public void removerFavorita(Serie serie) {
        favoritas.remove(serie);
    }

    public void adicionarAssistida(Serie serie) {
        if (!assistidas.contains(serie)) {
            assistidas.add(serie);
        }
    }

    public void removerAssistida(Serie serie) {
        assistidas.remove(serie);
    }

    public void adicionarDesejada(Serie serie) {
        if (!desejadas.contains(serie)) {
            desejadas.add(serie);
        }
    }

    public void removerDesejada(Serie serie) {
        desejadas.remove(serie);
    }
	
}
