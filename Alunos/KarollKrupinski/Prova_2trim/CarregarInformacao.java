import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CarregarInformacao {

    public void adicionarSerie(Scanner scanner, List<Series> ultimaBusca, IGerenciadorDeSeries lista, String nomeLista) {
        if (ultimaBusca == null || ultimaBusca.isEmpty()) {
            System.out.println("Por favor, realize uma busca antes de adicionar uma série.");
            return;
        }

        System.out.print("Digite o ID da série para adicionar à lista de " + nomeLista + ": ");
        try {
            int idAdicionar = scanner.nextInt();
            scanner.nextLine();

            Series serieParaAdicionar = null;
            for (Series serie : ultimaBusca) {
                if (serie.getId() == idAdicionar) {
                    serieParaAdicionar = serie;
                    break;
                }
            }

            if (serieParaAdicionar != null) {
                if (!lista.getLista().contains(serieParaAdicionar)) {
                    lista.adicionar(serieParaAdicionar);
                    System.out.println("Série '" + serieParaAdicionar.getName() + "' adicionada aos " + nomeLista + " com sucesso!");
                } else {
                    System.out.println("A série '" + serieParaAdicionar.getName() + "' já está na lista de " + nomeLista + ".");
                }
            } else {
                System.out.println("Série com ID " + idAdicionar + " não encontrada na última busca.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
            scanner.nextLine();
        }
    }

    public void removerSerie(Scanner scanner, IGerenciadorDeSeries lista, String nomeLista) {
        if (lista.getLista().isEmpty()) {
            System.out.println("A lista de " + nomeLista + " está vazia.");
            return;
        }

        System.out.print("Digite o ID da série para remover da lista de " + nomeLista + ": ");
        try {
            int idRemover = scanner.nextInt();
            scanner.nextLine();

            lista.remover(idRemover);

        } catch (InputMismatchException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
            scanner.nextLine();
        }
    }
}