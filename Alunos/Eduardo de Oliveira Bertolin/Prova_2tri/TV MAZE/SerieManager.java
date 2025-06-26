import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

// gerenciar as listas de séries do usuário
public class SerieManager implements Serializable {
    // favoritas
    private List<Serie> favoritos = new ArrayList<>();
    // assistidas
    private List<Serie> assistidas = new ArrayList<>();
    // deseja assistir
    private List<Serie> desejo = new ArrayList<>();

    // getters
    public List<Serie> getFavoritos() {
        return favoritos;
    }

    public List<Serie> getAssistidas() {
        return assistidas;
    }

    public List<Serie> getDesejo() {
        return desejo;
    }

    // setters para sobrescrever listas (útil para remover duplicatas)
    public void setFavoritos(List<Serie> favoritos) {
        this.favoritos = new ArrayList<>(favoritos);
    }
    public void setAssistidas(List<Serie> assistidas) {
        this.assistidas = new ArrayList<>(assistidas);
    }
    public void setDesejo(List<Serie> desejo) {
        this.desejo = new ArrayList<>(desejo);
    }

    // Adiciona aos favoritos (se ainda não tiver lá)
    public boolean adicionarFavorito(Serie s) {
        if (favoritos.contains(s)) {
            JOptionPane.showMessageDialog(null, "Você já adicionou esta série à lista de favoritos!");
            return false;
        } else {
            favoritos.add(s);
            return true;
        }
    }

    // Remove dos favoritos
    public void removerFavorito(Serie s) {
        favoritos.remove(s);
    }

    // Adiciona a lista de assistidas (se ainda não tiver lá)
    public boolean adicionarAssistida(Serie s) {
        if (assistidas.contains(s)) {
            JOptionPane.showMessageDialog(null, "Você já adicionou esta série à lista de assistidas!");
            return false;
        } else {
            assistidas.add(s);
            return true;
        }
    }

    // Remove da lista de assistidas
    public void removerAssistida(Serie s) {
        assistidas.remove(s);
    }

    // Adiciona a lista de desejo (se ainda não tiver lá)
    public boolean adicionarDesejo(Serie s) {
        if (desejo.contains(s)) {
            JOptionPane.showMessageDialog(null, "Você já adicionou esta série à lista de desejo!");
            return false;
        } else {
            desejo.add(s);
            return true;
        }
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
