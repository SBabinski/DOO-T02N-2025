package service;

import model.ObjetosComuns;
import model.Serie;
import model.TipoLista;
import model.helper.GeralHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SerieService {

    public static Scanner scanner = new Scanner(System.in);

    public static ObjetosComuns viewAndEditSerieListByTypeOfList(ObjetosComuns commonsObjects, TipoLista tipoLista) {
        List<Serie> series = commonsObjects.getListaSeries(tipoLista).getSerieList();
        series.forEach(Serie::printNameAndYear);
        System.out.println("""
                1 - Ver Dados Detalhados
                2 - Remover Série da Lista
                3 - Sair
                """);
        var opcao = scanner.nextInt();
        Long idSerie;
        Serie serieEncontrada = null;
        switch (opcao) {
            case 1:
                System.out.println("Digite o ID da série que deseja detalhar.");
                idSerie = Long.valueOf(GeralHelper.inputInteger());
                for (Serie serie : series) {
                    if (Objects.equals(serie.getId(), idSerie)) {
                        serieEncontrada = serie;
                        serieEncontrada.printDetalhado();
                    }
                }
                if (Objects.isNull(serieEncontrada)) {
                    System.out.println("Não foi encontrada série com esse ID.");
                }
                break;
            case 2:
                System.out.println("Digite o ID da série que deseja remover: ");
                idSerie = Long.valueOf(GeralHelper.inputInteger());
                removeSerieFromSerieList(series, idSerie, tipoLista, commonsObjects);
                return commonsObjects;
            case 3:
                return commonsObjects;
            default:
                System.out.println("Valor Inválido");
        }
        return commonsObjects;
    }

    private static List<Serie> removeSerieFromSerieList(List<Serie> series, Long idSerie, TipoLista tipoLista, ObjetosComuns commonsObjects) {
        var serieListAux = new ArrayList<Serie>(series);
        boolean serieRemovida = false;

        for (Serie serie : serieListAux) {
            if (Objects.equals(serie.getId(), idSerie)) {
                commonsObjects.removeSerieDaLista(serie, tipoLista);
                serieRemovida = true;
                break;
            }
        }

        if (!serieRemovida) {
            System.out.println("Série com o ID digitado não foi encontrada.");
        }

        return series;
    }

}
