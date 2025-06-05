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
        return nome + " - R$" + preco;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Atividade 1 ===");
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Números pares: " + pares);

        System.out.println("\n=== Atividade 2 ===");
        List<String> nomes = List.of("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);

        System.out.println("\n=== Atividade 3 ===");
        List<String> palavras = List.of("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println("Contagem de palavras:");
        contagemPalavras.forEach((palavra, contagem) ->
                System.out.println(palavra + ": " + contagem));

        System.out.println("\n=== Atividade 4 ===");
        List<Produto> produtos = List.of(
                new Produto("Notebook", 2500.00),
                new Produto("Mouse", 80.00),
                new Produto("Teclado", 150.00),
                new Produto("Monitor", 450.00)
        );
        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());
        System.out.println("Produtos com preço > R$100,00:");
        produtosCaros.forEach(System.out::println);

        System.out.println("\n=== Atividade 5 ===");
        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.printf("Valor total dos produtos: R$%.2f\n", total);

        System.out.println("\n=== Atividade 6 ===");
        List<String> linguagens = List.of("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
    }
}