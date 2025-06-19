package controller;

import model.DadosApp;
import model.GerenciadorListaSeries;
import model.Series;
import service.BuscarSerieService;
import service.GerenciadorDados;
import view.SerieView;
import view.SeriesTVInterface;

import java.util.List;
import javax.swing.*;

public class SerieController {

    private SerieView view;
    private BuscarSerieService buscarSerieService;
    private GerenciadorDados gerenciadorDados;
    private DadosApp dados;
    private GerenciadorListaSeries favoritosManager;
    private GerenciadorListaSeries assistidasManager;
    private GerenciadorListaSeries desejaAssistirManager;
    private List<Series> ultimaBusca;

    public SerieController() {
        this.view = new SeriesTVInterface(this);
        this.buscarSerieService = new BuscarSerieService();
        this.gerenciadorDados = new GerenciadorDados();
        this.dados = gerenciadorDados.carregarDados();
        this.favoritosManager = new GerenciadorListaSeries(dados.getFavoritos(), "favoritas");
        this.assistidasManager = new GerenciadorListaSeries(dados.getAssistidas(), "assistidas");
        this.desejaAssistirManager = new GerenciadorListaSeries(dados.getDesejaAssistir(), "deseja assistir");
    }

    public void iniciar() {
        String nomeUsuario = view.pedirNomeUsuario();
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            view.exibirMensagem("Nome não fornecido. Encerrando o sistema.");
            System.exit(0);
            return;
        }
        if (!dados.getNomesUsuarios().contains(nomeUsuario)) {
            dados.getNomesUsuarios().add(nomeUsuario);
        }
        view.exibirBoasVindas(nomeUsuario);

        view.showMainApplication();
    }

    public void buscarSeries(String nomeSerie) {
        new SwingWorker<List<Series>, Void>() {
            @Override
            protected List<Series> doInBackground() throws Exception {
                return buscarSerieService.buscarSeries(nomeSerie);
            }

            @Override
            protected void done() {
                try {
                    ultimaBusca = get();
                    view.exibirSeriesEncontradas(ultimaBusca);
                } catch (Exception e) {
                    view.exibirMensagem("Erro ao buscar séries: " + e.getMessage() + "\nVerifique a conexão com a internet ou o nome da série.");
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public void adicionarSerie(int idAdicionar, String tipoLista) {
        if (ultimaBusca == null || ultimaBusca.isEmpty()) {
            view.exibirMensagem("Por favor, realize uma busca antes de adicionar uma série.");
            return;
        }

        Series serieParaAdicionar = ultimaBusca.stream()
                .filter(serie -> serie.getId() == idAdicionar)
                .findFirst()
                .orElse(null);

        if (serieParaAdicionar == null) {
            view.exibirMensagem("Série com ID " + idAdicionar + " não encontrada na última busca.");
            return;
        }

        GerenciadorListaSeries manager;
        switch (tipoLista) {
            case "Favoritos":
                manager = favoritosManager;
                break;
            case "Assistidas":
                manager = assistidasManager;
                break;
            case "Deseja Assistir":
                manager = desejaAssistirManager;
                break;
            default:
                view.exibirMensagem("Tipo de lista inválido.");
                return;
        }

        if (manager.adicionar(serieParaAdicionar)) {
            view.exibirMensagem("Série '" + serieParaAdicionar.getName() + "' adicionada aos " + manager.getNomeLista() + " com sucesso!");
        } else {
            view.exibirMensagem("A série '" + serieParaAdicionar.getName() + "' já está na lista de " + manager.getNomeLista() + ".");
        }
    }

    public void removerSerie(int idRemover, String tipoLista) {
        GerenciadorListaSeries manager;
        switch (tipoLista) {
            case "Favoritos":
                manager = favoritosManager;
                break;
            case "Assistidas":
                manager = assistidasManager;
                break;
            case "Deseja Assistir":
                manager = desejaAssistirManager;
                break;
            default:
                view.exibirMensagem("Tipo de lista inválido.");
                return;
        }

        if (manager.getLista().isEmpty()) {
            view.exibirMensagem("A lista de " + manager.getNomeLista() + " está vazia.");
            return;
        }

        Series serieRemovida = manager.getLista().stream()
                .filter(s -> s.getId() == idRemover)
                .findFirst()
                .orElse(null);
        String serieName = (serieRemovida != null) ? serieRemovida.getName() : "Série com ID " + idRemover;


        if (manager.remover(idRemover)) {
            view.exibirMensagem("Série '" + serieName + "' removida da lista de " + manager.getNomeLista() + ".");
        } else {
            view.exibirMensagem("Série com ID " + idRemover + " não encontrada na lista de " + manager.getNomeLista() + ".");
        }
    }

    public void listarSeries(String tipoLista, int criterio) {
        GerenciadorListaSeries manager;
        switch (tipoLista) {
            case "Favoritos":
                manager = favoritosManager;
                break;
            case "Assistidas":
                manager = assistidasManager;
                break;
            case "Deseja Assistir":
                manager = desejaAssistirManager;
                break;
            default:
                view.exibirMensagem("Tipo de lista inválido.");
                return;
        }

        List<Series> seriesOrdenadas = manager.getListaOrdenada(criterio);
        String criterioNome = view.getCriterionName(criterio);
        view.exibirListaSeries(seriesOrdenadas, manager.getNomeLista(), criterioNome);
    }

    public void salvarEEncerrar() {
        String nomeUsuario = dados.getNomesUsuarios().isEmpty() ? "usuário" : dados.getNomesUsuarios().get(0);

        int confirm = view.showConfirmDialog("Deseja salvar os dados antes de sair?", "Confirmar Saída", JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean saved = gerenciadorDados.salvarDados(dados);
            if (saved) {
                view.exibirMensagem("Dados salvos com sucesso!\nObrigado por usar o Sistema de Séries TV, " + nomeUsuario + "!");
            } else {
                view.exibirMensagem("Erro ao salvar dados. Saindo sem salvar.");
            }
            System.exit(0);
        } else if (confirm == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}