import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

// gerenciar as listas de séries do usuário
public class SerieManager implements Serializable {
    // favoritas
    private List<Serie> favoritos = new ArrayList<>();
    // assistidas
    private List<Serie> assistidas = new ArrayList<>();
    // deseja assistir
    private List<Serie> desejo = new ArrayList<>();

    //getter

    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejo() {
        return desejo;
    }



    // Adiciona aos favoritos (se ainda não tiver lá)
    public void adicionarFavorito(Serie s) {
        if (!favoritos.contains(s))
            favoritos.add(s);
    }

    // Remove dos favoritos
    public void removerFavorito(Serie s) {
        favoritos.remove(s);
    }

    

    // Adiciona a lista de assistidas (se ainda não tiver lá)
    public void adicionarAssistida(Serie s) {
        if (!assistidas.contains(s))
            assistidas.add(s);
    }

    // Remove da lista de assistidas
    public void removerAssistida(Serie s) {
        assistidas.remove(s);
    }



    // Adiciona a lista de desejo (se ainda não tiver lá)
    public void adicionarDesejo(Serie s) {
        if (!desejo.contains(s))
            desejo.add(s);
    }

    // Remove da lista de desejo
    public void removerDesejo(Serie s) {
        desejo.remove(s);
    }



    // Ordena pela ordem alfabética
    public List<Serie> ordenarPorNome(List<Serie> lista) {
        return lista.stream().sorted(Comparator.comparing(Serie::getNome)).collect(Collectors.toList());
    }

    // Ordena pela nota geral (do maior para o menor)
    public List<Serie> ordenarPorNota(List<Serie> lista) {
        return lista.stream().sorted(Comparator.comparing(Serie::getNotaGeral).reversed()).collect(Collectors.toList());
    }

    // Ordena pelo estado (Running, Ended)
    public List<Serie> ordenarPorEstado(List<Serie> lista) {
        return lista.stream().sorted(Comparator.comparing(Serie::getEstado)).collect(Collectors.toList());
    }

    // Ordena pela data de estreia
    public List<Serie> ordenarPorEstreia(List<Serie> lista) {
        return lista.stream().sorted(Comparator.comparing(Serie::getDataEstreia)).collect(Collectors.toList());
    }
}