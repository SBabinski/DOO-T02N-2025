package Aula11;
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

    @Override
    public String toString() {
        return nome + " - R$" + preco;
    }
}

public class Main {
    public static void main(String[] args) {

        // Atv1 - Filtrar números pares
        List<Integer> numeros = Arrays.asList(10, 15, 22, 8, 33, 42, 17, 60);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Atv1 - Números pares: " + pares);

        // Atv2 - Converter nomes para maiúsculas
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Atv2 - Nomes em maiúsculo: " + nomesMaiusculos);

        // Atv3 - Contar frequência de palavras
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> frequencia = palavras.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        System.out.println("Atv3 - Frequência de palavras: " + frequencia);

        // Atv4 - Filtrar produtos com preço > R$ 100
        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 3000),
                new Produto("Mouse", 50),
                new Produto("Teclado", 150),
                new Produto("Cadeira", 200)
        );
        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100)
                .collect(Collectors.toList());
        System.out.println("Atv4 - Produtos com preço > R$ 100: " + produtosCaros);

        // Atv5 - Soma dos preços dos produtos
        double soma = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("Atv5 - Soma total dos produtos: R$" + soma);

        // Atv6 - Ordenar palavras por tamanho
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadasPorTamanho = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("Atv6 - Linguagens ordenadas por tamanho: " + ordenadasPorTamanho);
    }
}