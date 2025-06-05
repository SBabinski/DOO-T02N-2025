import java.util.*;
import java.util.stream.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        // ATV1 - Filtrar números pares de uma lista usando Stream API
        List<Integer> numeros = Arrays.asList(3, 10, 7, 22, 8, 13, 4, 12, 19, 6);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("// ATV1 - Números pares: " + pares);

        // ATV2 - Converter lista de nomes para maiúsculas usando Stream API
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("// ATV2 - Nomes em maiúsculas: " + nomesMaiusculos);

        // ATV3 - Contar ocorrência de cada palavra única em uma lista usando Stream API
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        System.out.println("// ATV3 - Ocorrências das palavras: " + contagem);

        // ATV4 - Filtrar produtos com preço maior que R$ 100,00 usando Stream API
        List<Produto> produtos = Arrays.asList(
            new Produto("Mouse", 75.0),
            new Produto("Teclado", 120.0),
            new Produto("Monitor", 800.0),
            new Produto("Cabo USB", 30.0)
        );
        List<Produto> acima100 = produtos.stream()
                .filter(p -> p.getPreco() > 100.0)
                .collect(Collectors.toList());
        System.out.println("// ATV4 - Produtos com preço > R$ 100,00:");
        acima100.forEach(p -> System.out.println(p));

        // ATV5 - Soma do valor total dos produtos usando Stream API
        double soma = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("// ATV5 - Soma total dos preços dos produtos: R$ " + soma);

        // ATV6 - Ordenar lista de linguagens pelo tamanho da palavra usando Stream API
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("// ATV6 - Linguagens ordenadas por tamanho: " + ordenadas);
    }
}

// Classe Produto para as atividades ATV4 e ATV5
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
        return nome + " - R$ " + preco;
    }
}