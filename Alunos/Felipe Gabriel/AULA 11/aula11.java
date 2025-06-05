import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Main {

    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 4, 7, 10, 12, 15, 18, 21, 24);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println(pares);

        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList());
        System.out.println(nomesMaiusculos);

        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                                             .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        contagem.forEach((palavra, qtd) -> System.out.println(palavra + ": " + qtd));

        List<Produto> produtos = Arrays.asList(
            new Produto("Teclado", 89.90),
            new Produto("Monitor", 649.00),
            new Produto("Mouse", 59.90),
            new Produto("Cadeira Gamer", 1200.00)
        );
        List<Produto> produtosCaros = produtos.stream()
                                              .filter(p -> p.getPreco() > 100.0)
                                              .collect(Collectors.toList());
        produtosCaros.forEach(p -> System.out.println(p.getNome() + " - R$" + p.getPreco()));

        double soma = produtos.stream()
                              .mapToDouble(Produto::getPreco)
                              .sum();
        System.out.println("Soma total dos produtos: R$ " + soma);

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream()
                                           .sorted(Comparator.comparingInt(String::length))
                                           .collect(Collectors.toList());
        System.out.println(ordenadas);
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
