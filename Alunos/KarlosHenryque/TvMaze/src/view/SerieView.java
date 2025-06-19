package view;

import model.Series;
import java.util.List;
import javax.swing.JOptionPane;

public interface SerieView {
    String pedirNomeUsuario();
    void exibirBoasVindas(String nomeUsuario);
    void exibirMensagem(String mensagem);
    void exibirMensagem(String mensagem, String titulo, int tipo);
    void fecharScanner();
    String pedirNomeSerieBusca();
    void exibirSeriesEncontradas(List<Series> series);
    int pedirIdSerieParaAdicionar(String nomeLista);
    int pedirIdSerieParaRemover(String nomeLista);
    int pedirCriterioOrdenacao(String nomeLista);
    String getCriterionName(int criterio);
    void exibirListaSeries(List<Series> series, String nomeLista, String criterioOrdenacao);
    void displaySeriesCatalog(List<Series> seriesList);
    void showMainApplication();
    int showConfirmDialog(String message, String title, int optionType);
}
