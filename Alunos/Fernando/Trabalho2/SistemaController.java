package Trabalho2;

import java.io.IOException;
import java.util.*;

public class SistemaController {
    
    private Usuario usuario;
    private final IbgeClient client = new IbgeClient();
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

    public Noticia buscarNoticiaPorTitulo(String titulo) throws IOException {
        return client.buscarNoticiaPorTitulo(titulo);
    }

    public Noticia buscarNoticiaPorPalavraChave(String palavraChave) throws IOException {
        return client.buscarNoticiaPorPalavraChave(palavraChave);
    }

    public Noticia buscarNoticiaPorData(String dataPublicacao) throws IOException {
        return client.buscarNoticiaPorData(dataPublicacao);
    }

    public void adicionarLidas(Noticia noticia) {
        usuario.getLidas().add(noticia);
    }

    public void removerLidas(Noticia noticia) {
        boolean removido = usuario.getLidas().remove(noticia);
        System.out.println(removido ? "Notícia removida com sucesso." : "Notícia não encontrada para remoção.");
    }

    // public void removerLidas(Noticia noti) {
    //     Iterator<Noticia> it = usuario.getLidas().iterator();
    //     while (it.hasNext()) {
    //         Noticia noticia = it.next();
    //         if (noticia.getTitulo().equalsIgnoreCase(noti.getTitulo())) {
    //             it.remove();
    //             return;
    //         }
    //     }
    // }

    public void adicionarParaLerDepois(Noticia noticia) {
        usuario.getParaLerDepois().add(noticia);
    }

    public void removerParaLerDepois(Noticia noticia) {
        Iterator<Noticia> it = usuario.getParaLerDepois().iterator();
        while (it.hasNext()) {
            Noticia noti = it.next();
            if (noticia.getTitulo().equalsIgnoreCase(noti.getTitulo())) {
                it.remove();
                return;
            }
        }
    }

    public void adicionarFavoritas(Noticia noticia) {
        usuario.getFavoritas().add(noticia);
    }

    public void removerFavoritas(Noticia noticia) {
        Iterator<Noticia> it = usuario.getFavoritas().iterator();
        while (it.hasNext()) {
            Noticia noti = it.next();
            if (noticia.getTitulo().equalsIgnoreCase(noti.getTitulo())) {
                it.remove();
                return;
            }
        }
    }

    public void salvar() throws IOException {
        persistencia.salvar(usuario);
    }

    public List<Noticia> getListaOrdenada(List<Noticia> lista, String criterio) {
        switch (criterio) {
            case "titulo":
                lista.sort(Comparator.comparing(Noticia::getTitulo));
                break;
            case "data":
                lista.sort(Comparator.comparing(Noticia::getDataPublicacao));
                break;
            case "tipo":
                lista.sort(Comparator.comparing(Noticia::getTipo));
                break;
        }
        return lista;
    }

}