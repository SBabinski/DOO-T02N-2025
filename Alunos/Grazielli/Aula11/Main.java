import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // Atv1
        System.out.println("\nAtv1");
        List<Integer> numeros = Arrays.asList(5, 12, 7, 8, 4, 33, 22, 16);
        System.out.println("Lista original: " + numeros);

        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Números pares: " + pares);

        // Atv2
        System.out.println("\nAtv2");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(nome -> nome.toUpperCase())
                .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);

        // Atv3
        System.out.println("\nAtv3");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));
        for (String palavra : contagemPalavras.keySet()) {
            System.out.println(palavra + ": " + contagemPalavras.get(palavra));
        }

        // Atv4
        System.out.println("\nAtv4");
        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 2500.00),
                new Produto("Fone de ouvido", 90.00),
                new Produto("Monitor", 800.00),
                new Produto("Mousepad", 30.00));
        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());
        System.out.println("Produtos com preço acima de R$100,00:");
        for (Produto p : produtosCaros) {
            System.out.println(p.getNome() + " - R$" + p.getPreco());
        }

        // Atv5
        System.out.println("\nAtv5");
        double somaTotal = produtos.stream()
                .mapToDouble(p -> p.getPreco())
                .sum();
        System.out.println("Soma total dos preços: R$" + somaTotal);

        // Atv6
        System.out.println("\nAtv6");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream()
                .sorted((a, b) -> Integer.compare(a.length(), b.length()))
                .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho: " + ordenadas);
    }
}

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
}
