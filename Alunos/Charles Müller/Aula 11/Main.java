import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

// Classe Produto para as atividades 4 e 5
class Produto {
    private String nome;
    private BigDecimal preco;

    public Produto(String nome, BigDecimal preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return String.format("Produto{nome='%s', preco=R$ %.2f}", nome, preco);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== MENU DE ATIVIDADES ===");
            System.out.println("1 - ATV1: Filtrar números pares");
            System.out.println("2 - ATV2: Converter nomes para maiúsculas");
            System.out.println("3 - ATV3: Contar palavras únicas");
            System.out.println("4 - ATV4: Filtrar produtos com preço > R$ 100");
            System.out.println("5 - ATV5: Somar preços dos produtos");
            System.out.println("6 - ATV6: Ordenar linguagens por tamanho");
            System.out.println("7 - Executar todas as atividades");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        executarAtv1();
                        break;
                    case 2:
                        executarAtv2();
                        break;
                    case 3:
                        executarAtv3();
                        break;
                    case 4:
                        executarAtv4();
                        break;
                    case 5:
                        executarAtv5();
                        break;
                    case 6:
                        executarAtv6();
                        break;
                    case 7:
                        executarTodasAtividades();
                        break;
                    case 0:
                        continuar = false;
                        System.out.println("Programa encerrado!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.nextLine(); // Limpa o buffer
            }
        }

        scanner.close();
    }

    // ATV1 - Filtrar números pares usando Stream API
    public static void executarAtv1() {
        System.out.println("\n=== ATV1: Filtrar números pares ===");

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        if (numeros == null || numeros.isEmpty()) {
            System.out.println("Lista de números está vazia ou nula!");
            return;
        }

        List<Integer> numerosPares = numeros.stream()
            .filter(Objects::nonNull)
            .filter(num -> num % 2 == 0)
            .collect(Collectors.toList());

        System.out.println("Lista original: " + numeros);
        System.out.println("Números pares: " + numerosPares);
        System.out.println("Total de números pares: " + numerosPares.size());
    }

    // ATV2 - Converter nomes para maiúsculas usando Stream API
    public static void executarAtv2() {
        System.out.println("\n=== ATV2: Converter nomes para maiúsculas ===");

        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        if (nomes == null || nomes.isEmpty()) {
            System.out.println("Lista de nomes está vazia ou nula!");
            return;
        }

        List<String> nomesMaiusculos = nomes.stream()
            .filter(Objects::nonNull)
            .filter(nome -> !nome.trim().isEmpty())
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        System.out.println("Nomes originais: " + nomes);
        System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);
    }

    // ATV3 - Contar palavras únicas usando Stream API
    public static void executarAtv3() {
        System.out.println("\n=== ATV3: Contar palavras únicas ===");

        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        if (palavras == null || palavras.isEmpty()) {
            System.out.println("Lista de palavras está vazia ou nula!");
            return;
        }

        Map<String, Long> contadorPalavras = palavras.stream()
            .filter(Objects::nonNull)
            .filter(palavra -> !palavra.trim().isEmpty())
            .collect(Collectors.groupingBy(
                palavra -> palavra.toLowerCase(),
                Collectors.counting()
            ));

        System.out.println("Lista de palavras: " + palavras);
        System.out.println("Contagem de palavras únicas:");
        contadorPalavras.forEach((palavra, count) ->
            System.out.println("  " + palavra + ": " + count + " vez(es)")
        );
    }

    // ATV4 - Filtrar produtos com preço > R$ 100 usando Stream API
    public static void executarAtv4() {
        System.out.println("\n=== ATV4: Filtrar produtos com preço > R$ 100 ===");

        List<Produto> produtos = criarListaProdutos();

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Lista de produtos está vazia ou nula!");
            return;
        }

        List<Produto> produtosCaros = produtos.stream()
            .filter(Objects::nonNull)
            .filter(produto -> produto.getPreco() != null)
            .filter(produto -> produto.getPreco().compareTo(new BigDecimal("100.00")) > 0)
            .collect(Collectors.toList());

        System.out.println("Todos os produtos:");
        produtos.forEach(System.out::println);

        System.out.println("\nProdutos com preço > R$ 100,00:");
        if (produtosCaros.isEmpty()) {
            System.out.println("Nenhum produto encontrado com preço superior a R$ 100,00");
        } else {
            produtosCaros.forEach(System.out::println);
        }
    }

    // ATV5 - Somar preços dos produtos usando Stream API
    public static void executarAtv5() {
        System.out.println("\n=== ATV5: Somar preços dos produtos ===");

        List<Produto> produtos = criarListaProdutos();

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Lista de produtos está vazia ou nula!");
            return;
        }

        BigDecimal somaTotal = produtos.stream()
            .filter(Objects::nonNull)
            .filter(produto -> produto.getPreco() != null)
            .map(Produto::getPreco)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Lista de produtos:");
        produtos.forEach(System.out::println);

        System.out.printf("\nSoma total dos preços: R$ %.2f%n", somaTotal);
    }

    // ATV6 - Ordenar linguagens por tamanho usando Stream API
    public static void executarAtv6() {
        System.out.println("\n=== ATV6: Ordenar linguagens por tamanho ===");

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        if (linguagens == null || linguagens.isEmpty()) {
            System.out.println("Lista de linguagens está vazia ou nula!");
            return;
        }

        List<String> linguagensOrdenadas = linguagens.stream()
            .filter(Objects::nonNull)
            .filter(lang -> !lang.trim().isEmpty())
            .sorted(Comparator.comparing(String::length))
            .collect(Collectors.toList());

        System.out.println("Linguagens originais: " + linguagens);
        System.out.println("Linguagens ordenadas por tamanho: " + linguagensOrdenadas);

        System.out.println("\nDetalhamento:");
        linguagensOrdenadas.forEach(lang ->
            System.out.println("  " + lang + " (" + lang.length() + " caracteres)")
        );
    }

    // Método auxiliar para criar lista de produtos
    private static List<Produto> criarListaProdutos() {
        return Arrays.asList(
            new Produto("Notebook", new BigDecimal("2500.00")),
            new Produto("Mouse", new BigDecimal("45.90")),
            new Produto("Teclado", new BigDecimal("150.00")),
            new Produto("Monitor", new BigDecimal("800.00"))
        );
    }

    // Executar todas as atividades em sequência
    public static void executarTodasAtividades() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EXECUTANDO TODAS AS ATIVIDADES");
        System.out.println("=".repeat(50));

        executarAtv1();
        executarAtv2();
        executarAtv3();
        executarAtv4();
        executarAtv5();
        executarAtv6();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("TODAS AS ATIVIDADES CONCLUÍDAS");
        System.out.println("=".repeat(50));
    }
}