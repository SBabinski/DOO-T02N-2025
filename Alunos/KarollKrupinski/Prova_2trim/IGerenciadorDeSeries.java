import java.util.List;

public interface IGerenciadorDeSeries {

    void adicionar(Series series);
    void remover(int idSeries);
    void listar();
    List<Series> getLista();
}