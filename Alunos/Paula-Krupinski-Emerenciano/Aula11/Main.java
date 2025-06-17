import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Main {

    public static void main(String[] args) {

        
        System.out.println("// ATV1");
        List<Integer> numeros = Arrays.asList(3, 7, 12, 18, 21, 26, 30, 41, 44, 50);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println("Números pares: " + pares);

        
        System.out.println("\n// ATV2");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);

        
        System.out.println("\n// ATV3");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                                             .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        contagem.forEach((palavra, qtd) -> System.out.println(palavra + ": " + qtd));

        System.out.println("\n// ATV4");
        List<Produto> produtos = Arrays.asList(
            new Produto("Perfume Importado", 250.0),
            new Produto("Shampoo Premium", 85.0),
            new Produto("Creme Hidratante", 120.0),
            new Produto("Kit Maquiagem", 180.0)
        );

        List<Produto> produtosCaros = produtos.stream()
                                              .filter(p -> p.getPreco() > 100.0)
                                              .collect(Collectors.toList());
        System.out.println("Produtos de beleza com preço > R$100,00:");
        produtosCaros.forEach(p -> System.out.println(p.getNome() + " - R$" + p.getPreco()));

       
        System.out.println("\n// ATV5");
        double soma = produtos.stream()
                              .mapToDouble(Produto::getPreco)
                              .sum();
        System.out.println("Soma total dos preços dos produtos: R$" + soma);

        
        System.out.println("\n// ATV6");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> ordenadas = linguagens.stream()
                                           .sorted(Comparator.comparingInt(String::length))
                                           .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho : " + ordenadas);
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
