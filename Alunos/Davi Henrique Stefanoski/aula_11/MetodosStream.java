package pack;

import java.util.*;
import java.util.stream.*;

class Produto {
    String nome;
    double preco;

    Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String toString() {
        return nome + " - R$" + preco;
    }
}

public class MetodosStream {
    public static void main(String[] args) {

  
        List<Integer> numeros = Arrays.asList(10, 23, 45, 8, 12, 90, 17, 34);
        Stream<Integer> streamNumeros = numeros.stream();
        List<Integer> pares = streamNumeros
            .filter(numero -> numero % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Atv1 - Números pares: " + pares);

       
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        Stream<String> streamNomes = nomes.stream();
        List<String> nomesMaiusculos = streamNomes
            .map(nome -> nome.toUpperCase())
            .collect(Collectors.toList());
        System.out.println("Atv2 - Nomes em maiúsculas: " + nomesMaiusculos);

        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Stream<String> streamPalavras = palavras.stream();
        Map<String, Long> contagemPalavras = streamPalavras
            .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));
        System.out.println("Atv3 - Contagem de palavras: " + contagemPalavras);

   
        Produto p1 = new Produto("Notebook", 2500.00);
        Produto p2 = new Produto("Mouse", 80.00);
        Produto p3 = new Produto("Teclado", 150.00);
        Produto p4 = new Produto("Fone", 90.00);

        List<Produto> produtos = Arrays.asList(p1, p2, p3, p4);

        Stream<Produto> streamProdutos = produtos.stream();
        List<Produto> produtosCaros = streamProdutos
            .filter(produto -> produto.getPreco() > 100.00)
            .collect(Collectors.toList());
        System.out.println("Atv4 - Produtos caros: " + produtosCaros);

     
        Stream<Produto> streamProdutos2 = produtos.stream();
        double somaTotal = streamProdutos2
            .mapToDouble(produto -> produto.getPreco())
            .sum();
        System.out.println("Atv5 - Soma total dos produtos: R$" + somaTotal);

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        Stream<String> streamLinguagens = linguagens.stream();
        List<String> linguagensOrdenadas = streamLinguagens
            .sorted((a, b) -> Integer.compare(a.length(), b.length()))
            .collect(Collectors.toList());
        System.out.println("Atv6 - Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
    }
}
