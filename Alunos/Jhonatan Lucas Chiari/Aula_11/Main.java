import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

public class Main {
    public static void main(String[] args) {
        // ATV1
        System.out.println("Atividade 1:");
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Números pares: " + pares);
        System.out.println(); 

        // ATV2
        System.out.println("Atividade 2:");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculos);
        System.out.println(); 

        // ATV3
        System.out.println("Atividade 3:");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));
        System.out.println("Contagem de palavras: " + contagem);
        System.out.println(); 

        // ATV4
        System.out.println("Atividade 4:");
        List<Produto> produtos = Arrays.asList(
            new Produto("Notebook", 3500.0),
            new Produto("Mouse", 50.0),
            new Produto("Monitor", 800.0),
            new Produto("Teclado", 150.0)
        );
        
        List<Produto> produtosCaros = produtos.stream()
                .filter(p -> p.getPreco() > 100.0)
                .collect(Collectors.toList());
        System.out.println("Produtos acima de R$100: " + 
            produtosCaros.stream()
                .map(Produto::getNome)
                .collect(Collectors.toList()));
        System.out.println(); 

        // ATV5
        System.out.println("Atividade 5:");
        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("Soma total dos produtos: R$" + total);
        System.out.println();

        // ATV6
        System.out.println("Atividade 6:");
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted((a, b) -> Integer.compare(a.length(), b.length()))
                .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas por tamanho: " + linguagensOrdenadas);
        System.out.println();
    }
}
