import java.util.*;
import java.util.stream.Collectors;

class Produto {
    String nome;
    double preco;

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
        return nome + " - R$" + String.format("%.2f", preco);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1 - Filtrar números pares");
            System.out.println("2 - Converter nomes para maiúsculas");
            System.out.println("3 - Contar palavras repetidas");
            System.out.println("4 - Filtrar produtos acima de R$100");
            System.out.println("5 - Calcular valor total dos produtos");
            System.out.println("6 - Ordenar palavras por tamanho");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                String input = scanner.next();
                
                if (input.equalsIgnoreCase("zero")) {
                    opcao = 0;
                } else {
                    opcao = Integer.parseInt(input);
                }

                switch (opcao) {
                    case 1:
                        filtrarPares();
                        break;
                    case 2:
                        converterMaiusculas();
                        break;
                    case 3:
                        contarPalavras();
                        break;
                    case 4:
                        filtrarProdutosCaros();
                        break;
                    case 5:
                        calcularTotalProdutos();
                        break;
                    case 6:
                        ordenarPorTamanho();
                        break;
                    case 0:
                        System.out.println("Programa encerrado.");
                        break;
                    default:
                        System.out.println("Opção inválida. Digite um número de 0 a 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números ou 'zero'.");
                scanner.nextLine();
            }
        } while (opcao != 0);

        scanner.close();
    }

    // ATV1
    private static void filtrarPares() {
        List<Integer> numeros = Arrays.asList(10, 15, 21, 32, 44, 57, 68, 79, 80, 93);
        
        // 1. Converte lista para stream
        // 2. Filtra apenas números pares
        // 3. Coleta resultados em nova lista
        List<Integer> pares = numeros.stream()
                                   .filter(n -> n % 2 == 0)
                                   .collect(Collectors.toList());
        
        System.out.println("\nLista original: " + numeros);
        System.out.println("Números pares encontrados: " + pares);
    }

    // ATV2
    private static void converterMaiusculas() {
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        
        // 1. Converte cada String para maiúsculas
        // 2. Coleta resultados
        List<String> nomesMaiusculos = nomes.stream()
                                          .map(String::toUpperCase)
                                          .collect(Collectors.toList());
        
        System.out.println("\nLista original: " + nomes);
        System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);
    }

    // ATV3
    private static void contarPalavras() {
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        
        // 1. Agrupa palavras idênticas
        // 2. Conta ocorrências de cada uma
        Map<String, Long> contagem = palavras.stream()
                                           .collect(Collectors.groupingBy(
                                               palavra -> palavra,
                                               Collectors.counting()
                                           ));
        
        System.out.println("\nLista de palavras: " + palavras);
        System.out.println("Frequência das palavras:");
        contagem.forEach((palavra, qtd) -> System.out.println(palavra + ": " + qtd + " ocorrências"));
    }

    // ATV4
    private static void filtrarProdutosCaros() {
        List<Produto> produtos = Arrays.asList(
            new Produto("Mouse", 80.00),
            new Produto("Teclado", 150.00),
            new Produto("Monitor", 450.00),
            new Produto("Headset", 120.00)
        );
        
        // 1. Filtra produtos com preço > 100
        // 2. Coleta em nova lista
        List<Produto> produtosFiltrados = produtos.stream()
                                               .filter(p -> p.getPreco() > 100.00)
                                               .collect(Collectors.toList());
        
        System.out.println("\nTodos os produtos:");
        produtos.forEach(System.out::println);
        System.out.println("\nProdutos com preço acima de R$100:");
        produtosFiltrados.forEach(System.out::println);
    }

    // ATV5
    private static void calcularTotalProdutos() {
        List<Produto> produtos = Arrays.asList(
            new Produto("Mouse", 80.00),
            new Produto("Teclado", 150.00),
            new Produto("Monitor", 450.00),
            new Produto("Headset", 120.00)
        );
        
        // 1. Converte stream de Produto para DoubleStream
        // 2. Soma todos os valores
        double total = produtos.stream()
                            .mapToDouble(Produto::getPreco)
                            .sum();
        
        System.out.println("\nLista de produtos:");
        produtos.forEach(System.out::println);
        System.out.printf("\nValor total dos produtos: R$%.2f\n", total);
    }

    // ATV6
    private static void ordenarPorTamanho() {
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        
        // 1. Ordena comparando o length() de cada String
        // 2. Coleta resultados
        List<String> ordenadas = linguagens.stream()
                                         .sorted(Comparator.comparingInt(String::length))
                                         .collect(Collectors.toList());
        
        System.out.println("\nLista original: " + linguagens);
        System.out.println("Palavras ordenadas por tamanho: " + ordenadas);
    }
}