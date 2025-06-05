import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {

        //ATV1

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        List<Integer> pares = numeros.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());

        System.out.println("\nNúmeros pares: " + pares);


        //ATV2

        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusc = nomes.stream().map(nome -> nome.toUpperCase()).collect(Collectors.toList());

        System.out.println("\nNomes em maíusculas: " + nomesMaiusc);


        //ATV3

        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagem = palavras.stream().collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("\nContagem de palavras: ");
        contagem.forEach((palavra, quant) -> {
            System.out.println(palavra + ": " + quant);
        });


        //ATV4

        List<Produto> produtos = Arrays.asList(
            new Produto("Placa de vídeo", 5000.00),
            new Produto("Teclado", 80.00),
            new Produto("Mouse", 50.00),
            new Produto("Monitor", 2500.00)
        );

        List<Produto> produtosCaros = produtos.stream().filter(p -> p.getPreco() > 100.00).collect(Collectors.toList());
        
        System.out.println("\nProdutos acima de R$100,00:");
        produtosCaros.forEach(System.out::println);


        //ATV5

        double somaTotal = produtos.stream().mapToDouble(p -> p.getPreco()).sum();

        System.out.println("\nSoma dos valores dos produtos: R$" + somaTotal);


        //ATV6

        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> listaOrdenada = linguagens.stream().sorted(Comparator.comparingInt(str -> str.length())).collect(Collectors.toList());

        System.out.println("\nLista das linguagens ordenadas: ");
        listaOrdenada.forEach(System.out::println);

        System.out.println();
    }

    public static class Produto {
        
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
        public String toString() {
            return nome + " - R$" + preco;
        }

    }
}
