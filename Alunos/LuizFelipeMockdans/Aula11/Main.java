import java.util.*;
import java.util.stream.*;

class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return nome + " - R$ " + preco;
    }
}

public class Main {
    public static void main(String[] args) {

        // ATV1 - Números pares com Stream API
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("// ATV1 - Números pares: " + pares);

        // ATV2 - Nomes em letras maiúsculas com Stream API
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("// ATV2 - Nomes em maiúsculas: " + nomesMaiusculos);

        // ATV3 - Contar ocorrências de palavras com Stream API
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println("// ATV3 - Contagem de palavras:");
        contagem.forEach((k, v) -> System.out.println(k + ": " + v));

        // ATV4 - Filtrar produtos com preço > R$100,00 com Stream API
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 80.0),
                new Produto("Monitor", 500.0),
                new Produto("Mouse", 40.0),
                new Produto("HD Externo", 150.0)
        );
        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.0)
                .collect(Collectors.toList());
        System.out.println("// ATV4 - Produtos com preço > R$100,00:");
        produtosCaros.forEach(System.out::println);

        // ATV5 - Soma total dos produtos
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("// ATV5 - Soma total dos produtos: R$ " + somaTotal);

        // ATV6 - Ordenar linguagens por tamanho com Stream API
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("// ATV6 - Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
    }
}
