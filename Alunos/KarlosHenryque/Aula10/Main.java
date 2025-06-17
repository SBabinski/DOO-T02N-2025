import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static class Produto {
        private String nome;
        private double preco;

        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public double getPreco() {
            return preco;
        }

        @Override
        public String toString() {
            return nome + " - R$ " + preco;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 2500.00),
                new Produto("Mouse", 50.00),
                new Produto("Teclado", 150.00),
                new Produto("Pen Drive", 80.00));

        while (true) {
            System.out.println("\nMenu:");
            System.out.println(" 1- Verificação de números pares");
            System.out.println(" 2- Verificação de nomes em maiúsculas");
            System.out.println(" 3- Verificação de palavras");
            System.out.println(" 4- Produtos com preço maior que R$ 100,00");
            System.out.println(" 5- Soma total dos preços dos produtos");
            System.out.println(" 6- Linguagens ordenadas por tamanho");
            System.out.println(" 0- Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                continue;
            } 

            switch (opcao) {

                case 0:
                    System.out.println("Encerrando o programa.");
                    scanner.close();
                    return;

                case 1:
                    System.out.println("Atividade 1: Verificação de números pares");
                    System.out.println("Verificação de números pares do 1 ao 8: ");
                    List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8);

                    List<Integer> numerosPares = numeros.stream()
                            .filter(n -> n % 2 == 0)
                            .toList();

                    System.out.println("Números pares: " + numerosPares);
                    break;

                case 2:
                    System.out.println("Atividade 2: Verificação de nomes em maiúsculas");
                    List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

                    List<String> nomesMaiusculos = nomes.stream()
                            .map(String::toUpperCase)
                            .toList();

                    System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);
                    break;

                case 3:
                    System.out.println("Atividade 3: Verificação de palavras");
                    List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

                    Map<String, Long> contagem = palavras.stream()
                            .collect(Collectors.groupingBy(
                                    palavra -> palavra,
                                    Collectors.counting()));

                    System.out.println("Contagem das palavras: " + contagem);
                    break;

                case 4:
                    System.out.println("Atividade 4: Produtos com preço maior que R$ 100,00");
                    List<Produto> produtosCaros = produtos.stream()
                            .filter(p -> p.getPreco() > 100)
                            .collect(Collectors.toList());

                    System.out.println("Produtos com preço maior que R$ 100,00:");
                    produtosCaros.forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("Atividade 5: Soma total dos preços dos produtos");
                    double somaPrecos = produtos.stream()
                            .mapToDouble(Produto::getPreco)
                            .sum();
                    System.out.printf("Soma total dos preços dos produtos: R$ ", somaPrecos);
                    break;

                case 6:
                    System.out.println("Atividade 6: Linguagens ordenadas por tamanho");
                    List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
                    List<String> ordenadasPorTamanho = linguagens.stream()
                            .sorted(Comparator.comparingInt(String::length))
                            .collect(Collectors.toList());
                    System.out.println("Linguagens ordenadas por tamanho: " + ordenadasPorTamanho);
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
