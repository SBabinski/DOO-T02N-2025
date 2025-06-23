package seriesTV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListaSeries {

    private List<Serie> favoritas = new ArrayList<>();
    private List<Serie> jaAssistidas = new ArrayList<>();
    private List<Serie> desejoAssistir = new ArrayList<>();

    public void adicionarFavorita(Serie serie) {
        if (!favoritas.contains(serie)) {
            favoritas.add(serie);
            System.out.println("'" + serie.getNome() + "' adicionado com sucesso na lista de favoritos!");
        } else {
            System.out.println("'" + serie.getNome() + "' já está na lista de favoritos!");
        }
    }

    public void adicionarJaAssistida(Serie serie) {
        if (!jaAssistidas.contains(serie)) {
            jaAssistidas.add(serie);
            System.out.println("'" + serie.getNome() + "' adicionado com sucesso na lista de já assistidas!");
        } else {
            System.out.println("'" + serie.getNome() + "' já está na lista de já assistidas!");
        }
    }

    public void adicionarDesejoAssistir(Serie serie) {
        if (!desejoAssistir.contains(serie)) {
            desejoAssistir.add(serie);
            System.out.println("'" + serie.getNome() + "' adicionado com sucesso na lista de desejo assistir!");
        } else {
            System.out.println("'" + serie.getNome() + "' já está na lista de desejo assistir!");
        }
    }

    public void removerFavorita(Serie serie) {
        if (favoritas.remove(serie)) {
            System.out.println("'" + serie.getNome() + "' removido com sucesso da lista de favoritos!");
        } else {
            System.out.println("'" + serie.getNome() + "' não encontrado na lista de favoritos!");
        }
    }

    public void removerJaAssistidas(Serie serie) {
        if (jaAssistidas.remove(serie)) {
            System.out.println("'" + serie.getNome() + "' removido com sucesso da lista de já assistidas!");
        } else {
            System.out.println("'" + serie.getNome() + "' não encontrado na lista de já assistidas!");
        }
    }

    public void removerDesejoAssistir(Serie serie) {
        if (desejoAssistir.remove(serie)) {
            System.out.println("'" + serie.getNome() + "' removido com sucesso da lista de desejo assistir!");
        } else {
            System.out.println("'" + serie.getNome() + "' não encontrado na lista de desejo assistir!");
        }
    }

    public List<Serie> getFavoritas() {
        return favoritas;
    }

    public List<Serie> getJaAssistidas() {
        return jaAssistidas;
    }

    public List<Serie> getDesejoAssistir() {
        return desejoAssistir;
    }


    public void ordenarPorNome(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getNome));
    }

    public void ordenarPorNota(List<Serie> lista) {
        lista.sort(Comparator.comparingDouble(Serie::getNota).reversed());
    }

    public void ordenarPorStatus(List<Serie> lista) {
        lista.sort(Comparator.comparing(Serie::getStatus));
    }

    public void ordenarPorDataEstreia(List<Serie> lista) {
        lista.sort(Comparator.comparing(
                Serie::getDataEstreia,
                Comparator.nullsLast(Comparator.naturalOrder()) // ← Garante que séries com data null vão para o final
        ));
    }
}
