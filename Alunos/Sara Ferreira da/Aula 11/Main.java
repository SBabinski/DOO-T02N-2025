import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // ------------------ ATV1 ------------------
        System.out.println("---- ATV1 ----");
        List<Integer> numeros = Arrays.asList(10, 15, 22, 33, 40, 55, 60, 71, 82);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Números pares: " + pares);

        // ------------------ ATV2 ------------------
        System.out.println("---- ATV2 ----");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);

        // ------------------ ATV3 ------------------
        System.out.println("---- ATV3 ----");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println("Contagem de palavras: " + contagem);

        // ------------------ ATV4 ------------------
        System.out.println("---- ATV4 ----");
        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 3500.00),
                new Produto("Mouse", 80.00),
                new Produto("Teclado", 150.00),
                new Produto("Monitor", 900.00)
        );

        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("Produtos com preço maior que R$100,00:");
        produtosCaros.forEach(p -> System.out.println(p.getNome() + " - R$" + p.getPreco()));

        // ------------------ ATV5 ------------------
        System.out.println("---- ATV5 ----");
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("Soma total dos produtos: R$" + somaTotal);

        // ------------------ ATV6 ------------------
        System.out.println("---- ATV6 ----");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadoPorTamanho = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho: " + ordenadoPorTamanho);
    }
}

// Classe Produto (Usada na ATV4 e ATV5)
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
