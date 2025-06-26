package Aula11;

import java.util.*;
import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) {

        // Atv1 - Filtrar números pares
        List<Integer> numeros = Arrays.asList(3, 8, 14, 25, 36, 47, 52, 60);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(toList());
        System.out.println("Números pares: " + pares);

        // Atv2 - Converter nomes para maiúsculo
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculo = nomes.stream()
                                           .map(String::toUpperCase)
                                           .collect(toList());
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculo);

        // Atv3 - Contar palavras
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                                                     .collect(groupingBy(p -> p, counting()));
        System.out.println("Contagem de palavras: " + contagemPalavras);

        // Atv4 - Filtrar produtos com preço > 100
        List<Produto> produtos = Arrays.asList(
            new Produto("Notebook", 3500.00),
            new Produto("Mouse", 80.00),
            new Produto("Teclado", 150.00),
            new Produto("Monitor", 750.00)
        );
        List<Produto> produtosCaros = produtos.stream()
                                              .filter(p -> p.getPreco() > 100.00)
                                              .collect(toList());
        System.out.println("Produtos com preço > R$100: ");
        produtosCaros.forEach(p -> System.out.println(p.getNome() + ": R$" + p.getPreco()));

        // Atv5 - Soma total dos preços
        double somaTotal = produtos.stream()
                                   .mapToDouble(Produto::getPreco)
                                   .sum();
        System.out.println("Soma total dos preços dos produtos: R$" + somaTotal);

        // Atv6 - Ordenar linguagens por tamanho
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadasPorTamanho = linguagens.stream()
                                                     .sorted(Comparator.comparingInt(String::length))
                                                     .collect(toList());
        System.out.println("Linguagens ordenadas por tamanho: " + ordenadasPorTamanho);
    }
}

// Produto.java
class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
}
