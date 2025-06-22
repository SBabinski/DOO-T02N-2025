import java.util.Scanner;

public class ClimaView {

    private ClimaController controller;

    public ClimaView() {
        this.controller = new ClimaController();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("===== Consulta de Clima =====");
            System.out.println("1 - Pesquisar clima por cidade");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                if (opcao == 1) {
                    System.out.print("Digite o nome da cidade: ");
                    String cidade = scanner.nextLine();

                    Clima clima = controller.obterClimaPorCidade(cidade);

                    if (clima != null) {
                        System.out.println(clima);
                    } else {
                        System.out.println("Não foi possível realizar a busca pelo clima. Verifique o nome da cidade e tente novamente.");
                    }
                } else if (opcao == 0) {
                    System.out.println("Encerrando o programa. Até logo!");
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite 1 ou 0.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado. Tente novamente mais tarde.");
            }

            System.out.println();
        }

        scanner.close();
    }
}
