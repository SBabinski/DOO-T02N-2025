package Prova_2tri;

import java.io.IOException;
import java.util.*;

public class SistemaController {
    
    private Usuario usuario;
    private final TvMazeClient client = new TvMazeClient();
    private final PersistenciaService persistencia = new PersistenciaService();

    public void iniciar(String nomeUsuario) throws IOException {
        usuario = persistencia.carregar();
        if (usuario == null) {
            usuario = new Usuario();
            usuario.setNome(nomeUsuario);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Serie buscarSerie(String nome) throws IOException {
        return client.buscarSeriePorNome(nome);
    }

    public void adicionarFavoritos(Serie serie) {
        usuario.getFavoritas().add(serie);
    }

    public void removerFavoritos(Serie s) {
        Iterator<Serie> it = usuario.getFavoritas().iterator();
        while (it.hasNext()) {
            Serie serie = it.next();
            if (serie.getNome().equalsIgnoreCase(s.getNome())) {
                it.remove();
                return;
            }
        }
    }

    public void adicionarAssistidas(Serie serie) {
        usuario.getAssistidas().add(serie);
    }

    public void removerAssistidas(Serie serie) {
        Iterator<Serie> it = usuario.getAssistidas().iterator();
        while (it.hasNext()) {
            Serie s = it.next();
            if (s.getNome().equalsIgnoreCase(serie.getNome())) {
                it.remove();
                return;
            }
        }
    }

    public void adicionarDesejadas(Serie serie) {
        usuario.getDesejadas().add(serie);
    }

    public void removerDesejadas(Serie serie) {
        Iterator<Serie> it = usuario.getDesejadas().iterator();
        while (it.hasNext()) {
            Serie s = it.next();
            if (s.getNome().equalsIgnoreCase(serie.getNome())) {
                it.remove();
                return;
            }
        }
    }

    public void salvar() throws IOException {
        persistencia.salvar(usuario);
    }

    public List<Serie> getListaOrdenada(List<Serie> lista, String criterio) {
        switch (criterio) {
            case "nome":
                lista.sort(Comparator.comparing(Serie::getNome));
                break;
            case "nota":
                lista.sort(Comparator.comparingDouble(Serie::getNotaGeral).reversed());
                break;
            case "status":
                lista.sort(Comparator.comparing(Serie::getStatus));
                break;
            case "estreia":
                lista.sort(Comparator.comparing(Serie::getDataEstreia));
                break;
        }
        return lista;
    }

}