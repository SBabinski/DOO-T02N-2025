package spring_boot_api.tvmaze_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListasService {

    private final ListasRepository repository;
    private Listas listas;

    @Autowired
    public ListasService(ListasRepository repository) {
        this.repository = repository;
        this.listas = repository.carregar();
    }

    public void setNomeUsuario(String nome) {
        listas.setNome(nome);
    }

    public Listas getListas() {
        return listas;
    }

   
    public void adicionarFavorita(ShowsDTO serie) {
        if (!listas.getFavoritas().contains(serie)) {
            listas.getFavoritas().add(serie);
            repository.salvar(listas);
        }
    }

    public void removerFavorita(ShowsDTO serie) {
        if (listas.getFavoritas().remove(serie)) {
            repository.salvar(listas);
        }
    }

    public void adicionarAssistida(ShowsDTO serie) {
        if (!listas.getAssistidas().contains(serie)) {
            listas.getAssistidas().add(serie);
            repository.salvar(listas);
        }
    }

    public void removerAssistida(ShowsDTO serie) {
        if (listas.getAssistidas().remove(serie)) {
            repository.salvar(listas);
        }
    }

    public void adicionarDesejo(ShowsDTO serie) {
        if (!listas.getDesejoAssistir().contains(serie)) {
            listas.getDesejoAssistir().add(serie);
            repository.salvar(listas);
        }
    }

    public void removerDesejo(ShowsDTO serie) {
        if (listas.getDesejoAssistir().remove(serie)) {
            repository.salvar(listas);
        }
    }
}

