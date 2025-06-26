import java.util.List;
import java.util.Scanner;

public class removerSerie{

    public static void mostrarListaEPermitirRemocao(String nomeLista, List<Serie> lista, Scanner scanner, Usuario usuario) {
        System.out.println("\nLista de " + nomeLista + ":");
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série na lista.");
        } else {
            for (Serie serie : lista) {
                System.out.println(serie.getName());
            }

        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\nDeseja remover alguma série? (s/n)");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s") || resposta.equalsIgnoreCase("sim")) {
            System.out.println("Digite o nome da série que deseja remover:");
            String nomeRemover = scanner.nextLine();
                
            boolean removida = lista.removeIf(serie -> 
            serie.getName().toLowerCase().contains(nomeRemover.toLowerCase()));

            if (removida) {
            System.out.println("Série removida com sucesso.");
            Salvar.salvar(usuario);
            } else {
                System.out.println("Nenhuma série encontrada com o nome: " + nomeRemover);
             } 

            System.out.println("Deseja remover outra série? (s/n)");
            String removerOutra = scanner.nextLine();

            if (removerOutra.equalsIgnoreCase("n") || removerOutra.equalsIgnoreCase("não")) {
                continuar = false;
                System.out.println("\nVoltando ao menu...");
            } 
            } else if (resposta.equalsIgnoreCase("n") || resposta.equalsIgnoreCase("não")) {
                continuar = false;
                System.out.println("Voltando ao menu...");
             } else {
                System.out.println("Resposta inválida. Digite 's' para sim ou 'n' para não.");
        }
    }
}
    } 
}

    

