import model.ObjetosComuns;
import model.Serie;
import model.TipoLista;
import model.Usuario;
import model.helper.GeralHelper;
import model.helper.JsonHelper;
import service.ApiService;
import service.SerieService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static ObjetosComuns objetosComuns = new ObjetosComuns();

    public static void main(String[] args) {
        System.out.println("Programa Iníciado!");
        run();
    }

    public static void run() {
        if (Objects.isNull(objetosComuns.getUsuario())) {
            System.out.println("Digite seu nome: ");
            var nomeUsuario = scanner.nextLine();
            var usuario = new Usuario(nomeUsuario);
            objetosComuns.setUsuario(usuario);
            JsonHelper.gravarJsonUsuario(usuario);
        }
        if (!Objects.isNull(objetosComuns.getUsuario())) {
            System.out.println("Boas-vindas, " + objetosComuns.getUsuario().getNome());
        }
        while (true) {
            printMenu();
            executaAcoesDoMenu();
        }
    }

    public static void printMenu() {
        System.out.println("""
                1 - Buscar Série
                2 - Visualizar Séries Favoritadas
                3 - Visualizar Séries Já Assistidas
                4 - Visualizar Séries Que Deseja Assistir
                0 - Fechar Aplicação
                Digite a opção desejada:""");
    }

    public static void executaAcoesDoMenu() {
        int selectedOption = GeralHelper.inputInteger();
        switch (selectedOption) {
            case 0:
                System.exit(0);
                break;
            case 1:
                searchSeriesFromName();
                break;
            case 2:
                SerieService.viewAndEditSerieListByTypeOfList(objetosComuns, TipoLista.FAVORITAS);
                break;
            case 3:
                SerieService.viewAndEditSerieListByTypeOfList(objetosComuns, TipoLista.ASSISTIDAS);
                break;
            case 4:
                SerieService.viewAndEditSerieListByTypeOfList(objetosComuns, TipoLista.PARA_ASSISTIR);
                break;
            default:
                System.out.println("Por favor, digite uma opção válida.");
        }
    }

    private static void searchSeriesFromName() {
        System.out.println("Digite o nome da série: ");
        var nameSerie = scanner.next();
        List<Serie> series = ApiService.buscaListaSerie(nameSerie);
        System.out.println("Foram encontradas as seguintes séries, qual a série desejada?");
        for (int cont = 0; cont < series.size(); cont++) {
            var serie = series.get(cont);
            System.out.println((cont + 1) + " - " + serie.getNome() + ", " + serie.getDataEstreia().getYear());
        }
        System.out.println("Digite o número referente a série:");
        var opcao = GeralHelper.inputInteger();
        Serie serie = series.get(opcao - 1);
        System.out.println(serie.getNome() + ", " + serie.getDataEstreia().getYear() + " selecionada. O que deseja fazer?" +
                "\n1 - Ver mais informações" +
                "\n2 - Marcar como Favorita" +
                "\n3 - Marcar como Já Assistida" +
                "\n4 - Marcar como Deseja Assistir" +
                "\nSeleciona a opção desejada:");
        opcao = GeralHelper.inputInteger();
        switch (opcao) {
            case 1:
                serie.printDetalhado();
                break;
            case 2:
                objetosComuns.adicionaSerieNaLista(serie, TipoLista.FAVORITAS);
                break;
            case 3:
                objetosComuns.adicionaSerieNaLista(serie, TipoLista.ASSISTIDAS);
                break;
            case 4:
                objetosComuns.adicionaSerieNaLista(serie, TipoLista.PARA_ASSISTIR);
                break;
            default:
                System.out.println("Opção inválida!");
        }

    }
}