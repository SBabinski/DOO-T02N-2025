import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Classe Produto para ATV4 e ATV5
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
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {

        //ATV1
        System.out.println("ATIVIDADE 1");

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> numerosPares = numeros.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());

        System.out.println("Lista original: " + numeros);
        System.out.println("Lista de pares: " + numerosPares);
        System.out.println("----------------------------------\n");

        //ATV2
        System.out.println("ATIVIDADE 2");

        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream().map(String::toUpperCase).collect(Collectors.toList());

        System.out.println("Nomes originais: " + nomes);
        System.out.println("Nomes em maiúsculas: " + nomesMaiusculos);
        System.out.println("----------------------------------\n");

        //ATV3
        System.out.println("ATIVIDADE 3");

        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("Lista de palavras: " + palavras);
        System.out.println("Contagem de cada palavra: " + contagemPalavras);
        System.out.println("----------------------------------\n");

        //ATV4
        System.out.println("ATIVIDADE 4");

        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook", 4500.00),
                new Produto("Mouse", 99.90),
                new Produto("Teclado", 150.50),
                new Produto("Monitor", 850.00)
        );
        List<Produto> produtosCaros = produtos.stream().filter(p -> p.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("Lista de todos os produtos: " + produtos);
        System.out.println("Produtos com preço > R$ 100: " + produtosCaros);
        System.out.println("----------------------------------\n");

        //ATV5
        System.out.println("ATIVIDADE 5");
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("A soma total dos preços dos produtos é: R$ " + String.format("%.2f", somaTotal));
        System.out.println("----------------------------------\n");

        //ATV6
        System.out.println("ATIVIDADE 6");

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = linguagens.stream().sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("Linguagens originais: " + linguagens);
        System.out.println("Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
        System.out.println("----------------------------------");
    }
}
