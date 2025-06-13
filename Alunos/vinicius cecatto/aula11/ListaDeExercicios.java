import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

public class ListaDeExercicios {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- MENU DE ATIVIDADES COM STREAM API ---");
            System.out.println("1. Filtrar números pares");
            System.out.println("2. Converter nomes para maiúsculas");
            System.out.println("3. Contar ocorrência de palavras");
            System.out.println("4. Filtrar produtos por preço (> R$ 100,00)");
            System.out.println("5. Somar valor total dos produtos");
            System.out.println("6. Ordenar linguagens por tamanho");
            System.out.println("0. Sair");
            System.out.print("Escolha uma atividade: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // ATV1
                    System.out.println("\n--- ATV1: Filtrar números pares ---");
                    System.out.print("Digite pelo menos 8 números inteiros separados por espaço: ");
                    String inputNumeros = scanner.nextLine();
                    List<Integer> numeros = Arrays.stream(inputNumeros.split(" "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    List<Integer> numerosPares = numeros.stream()
                            .filter(n -> n % 2 == 0)
                            .collect(Collectors.toList());
                    System.out.println(numerosPares);
                    break;
                case 2:
                    // ATV2
                    System.out.println("\n--- ATV2: Converter nomes para maiúsculas ---");
                    System.out.print("Digite nomes separados por espaço: ");
                    String inputNomes = scanner.nextLine();
                    List<String> nomes = Arrays.asList(inputNomes.split(" "));
                    List<String> nomesMaiusculos = nomes.stream()
                            .map(String::toUpperCase)
                            .collect(Collectors.toList());
                    System.out.println(nomesMaiusculos);
                    break;
                case 3:
                    // ATV3
                    System.out.println("\n--- ATV3: Contar ocorrência de palavras ---");
                    System.out.print("Digite palavras separadas por espaço: ");
                    String inputPalavras = scanner.nextLine();
                    List<String> palavras = Arrays.asList(inputPalavras.split(" "));
                    Map<String, Long> contagemPalavras = palavras.stream()
                            .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
                    System.out.println(contagemPalavras);
                    break;
                case 4:
                    // ATV4
                    System.out.println("\n--- ATV4: Filtrar produtos por preço (> R$ 100,00) ---");
                    List<Produto> produtos4 = new ArrayList<>();
                    System.out.println("Crie 4 produtos.");
                    for (int i = 0; i < 4; i++) {
                        System.out.print("Nome do produto " + (i + 1) + ": ");
                        String nome = scanner.nextLine();
                        System.out.print("Preço do produto " + (i + 1) + ": ");
                        double preco = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        produtos4.add(new Produto(nome, preco));
                    }
                    List<Produto> produtosCaros = produtos4.stream()
                            .filter(p -> p.getPreco() > 100.00)
                            .collect(Collectors.toList());
                    produtosCaros.forEach(System.out::println);
                    break;
                case 5:
                    // ATV5
                    System.out.println("\n--- ATV5: Somar valor total dos produtos ---");
                    List<Produto> produtos5 = new ArrayList<>();
                    System.out.println("Crie produtos para somar (digite 'fim' no nome para parar).");
                    while (true) {
                        System.out.print("Nome do produto (ou 'fim' para terminar): ");
                        String nome = scanner.nextLine();
                        if (nome.equalsIgnoreCase("fim")) {
                            break;
                        }
                        System.out.print("Preço do produto: ");
                        double preco = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        produtos5.add(new Produto(nome, preco));
                    }
                    double somaTotalProdutos = produtos5.stream()
                            .mapToDouble(Produto::getPreco)
                            .sum();
                    System.out.println("R$ " + String.format("%.2f", somaTotalProdutos));
                    break;
                case 6:
                    // ATV6
                    System.out.println("\n--- ATV6: Ordenar linguagens por tamanho ---");
                    System.out.print("Digite linguagens separadas por espaço: ");
                    String inputLinguagens = scanner.nextLine();
                    List<String> linguagens = Arrays.asList(inputLinguagens.split(" "));
                    List<String> linguagensOrdenadas = linguagens.stream()
                            .sorted(Comparator.comparingInt(String::length))
                            .collect(Collectors.toList());
                    System.out.println(linguagensOrdenadas);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

// Classe Produto para ATV4 e ATV5
class Produto {
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
        return "Produto [nome=" + nome + ", preço=" + String.format("%.2f", preco) + "]";
    }
}