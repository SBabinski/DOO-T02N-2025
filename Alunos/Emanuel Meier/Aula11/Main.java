
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.Map.Entry;

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

    @Override
    public String toString() {
        return nome + " - R$" + preco;
    }
}

public class Main {
    public static void main(String[] args) {

        // ATV1 - Filtrar números pares
        System.out.println("// ATV1");
        List<Integer> numeros = Arrays.asList(10, 15, 20, 25, 30, 35, 40, 45);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Números pares: " + pares);

        // ATV2 - Converter nomes para maiúsculas
        System.out.println("\n// ATV2");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);

        // ATV3 - Contar palavras únicas
        System.out.println("\n// ATV3");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        contagem.forEach((palavra, qtd) -> System.out.println(palavra + ": " + qtd));

        // ATV4 - Filtrar produtos com preço > R$ 100,00
        System.out.println("\n// ATV4");
        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 2500.00),
                new Produto("Mouse", 80.00),
                new Produto("Teclado", 150.00),
                new Produto("Monitor", 300.00)
        );

        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("Produtos com preço > R$ 100,00:");
        produtosCaros.forEach(System.out::println);

        // ATV5 - Soma do valor total dos produtos
        System.out.println("\n// ATV5");
        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("Valor total dos produtos: R$" + total);

        // ATV6 - Ordenar lista pelo tamanho das palavras
        System.out.println("\n// ATV6");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadoPorTamanho = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho: " + ordenadoPorTamanho);
    }
}
