import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Main {

    // Atividades da aula 11

    // Classe Produto para as atividades 4 e 5
    static class Produto {
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

    }

    public static void main(String[] args) {

        // ATV1 - Filtrar números pares
        System.out.println("// ATV1");
        List<Integer> numeros = Arrays.asList(10, 15, 22, 33, 40, 55, 64, 78);
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

        // ATV3 - Contar frequência de palavras
        System.out.println("\n// ATV3");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                                             .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        contagem.forEach((palavra, qtd) -> System.out.println(palavra + ": " + qtd + "x"));

        // ATV4 - Filtrar produtos com preço > 100
        System.out.println("\n// ATV4");
        List<Produto> produtos = Arrays.asList(
            new Produto("Notebook", 2500.00),
            new Produto("Mouse", 85.00),
            new Produto("Teclado", 120.00),
            new Produto("Cadeira", 95.00)
        );
        List<Produto> caros = produtos.stream()
                                      .filter(p -> p.getPreco() > 100.00)
                                      .collect(Collectors.toList());
        System.out.println("Produtos com preço > R$ 100,00:");
        caros.forEach(System.out::println);

        // ATV5 - Soma total dos preços
        System.out.println("\n// ATV5");
        double soma = produtos.stream()
                              .mapToDouble(Produto::getPreco)
                              .sum();
        System.out.println("Soma total dos produtos: R$ " + soma);

        // ATV6 - Ordenar por tamanho da palavra
        System.out.println("\n// ATV6");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream()
                                           .sorted(Comparator.comparingInt(String::length))
                                           .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho: " + ordenadas);
    }
}
