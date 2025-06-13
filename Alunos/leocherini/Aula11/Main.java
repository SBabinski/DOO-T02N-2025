package codStream;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.Map.Entry;

class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco){
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome(){
        return nome;
    }

    public double getPreco(){
        return preco;
    }

    @Override
    public String toString(){
        return nome + " - R$ " + preco;
    }
}

public class Main {
    public static void main(String[] args) {

        // ATIVIDADE 1 - Filtrar números pares
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> numerosPares = filtrarNumerosPares(numeros);
        System.out.println("// ATV1 - Números pares: " + numerosPares);

        // ATIVIDADE 2 - Converter nomes para maiúsculas
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = converterNomesParaMaiusculo(nomes);
        System.out.println("// ATV2 - Nomes em maiúsculo: " + nomesMaiusculos);

        // ATIVIDADE 3 - Contar frequência de palavras
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> frequenciaPalavras = contarFrequenciaPalavras(palavras);
        System.out.println("// ATV3 - Frequência de palavras: " + frequenciaPalavras);

        // ATIVIDADE 4 - Filtrar produtos com preço > R$100
        List<Produto> produtos = Arrays.asList(
            new Produto("Celular", 1500.0),
            new Produto("Mouse", 80.0),
            new Produto("Monitor", 600.0),
            new Produto("Teclado", 120.0)
        );
        List<Produto> produtosCaros = filtrarProdutosPorPreco(produtos, 100.0);
        System.out.println("// ATV4 - Produtos com preço > R$100: " + produtosCaros);

        // ATIVIDADE 5 - Soma total dos preços
        double somaPrecos = somarPrecoProdutos(produtos);
        System.out.println("// ATV5 - Soma total dos preços: R$ " + somaPrecos);

        // ATIVIDADE 6 - Ordenar lista conforme tamanho
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = ordenarPorTamanho(linguagens);
        System.out.println("// ATV6 - Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
    }

    // Método para ATV1
    public static List<Integer> filtrarNumerosPares(List<Integer> numeros){
        return numeros.stream()
                      .filter(n -> n % 2 == 0)
                      .collect(Collectors.toList());
    }

    // Método para ATV2
    public static List<String> converterNomesParaMaiusculo(List<String> nomes){
        return nomes.stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
    }

    // Método para ATV3
    public static Map<String, Long> contarFrequenciaPalavras(List<String> palavras){
        return palavras.stream()
                       .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    // Método para ATV4
    public static List<Produto> filtrarProdutosPorPreco(List<Produto> produtos, double precoMinimo){
        return produtos.stream()
                       .filter(p -> p.getPreco() > precoMinimo)
                       .collect(Collectors.toList());
    }

    // Método para ATV5
    public static double somarPrecoProdutos(List<Produto> produtos){
        return produtos.stream()
                       .mapToDouble(Produto::getPreco)
                       .sum();
    }

    // Método para ATV6
    public static List<String> ordenarPorTamanho(List<String> lista){
        return lista.stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .collect(Collectors.toList());
    }
}
