import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        atv1();
    }

    public static void atv1(){
        System.out.println("Atividade 01");
        List<Integer> numeros = Arrays.asList(19, 28, 106, 31, 74, 21, 85, 14, 38, 339);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 ==0)
                                     .collect(Collectors.toList());
        System.out.println("Numeros pares: "+pares);

        atv2();
    }

    public static void atv2(){
        System.out.println();
        System.out.println("Atividade 02");
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream()
                                            .map(String:: toUpperCase)
                                            .collect(Collectors.toList());
        System.out.println("Nomes em Maiúsculo: " +nomesMaiusculos);

        atv3();
    }

    public static void atv3(){
        System.out.println();
        System.out.println("Atividade 03");
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> contagem = palavras.stream()
                                             .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        contagem.forEach((palavra, quantidade) -> System.out.println(palavra + ": "+quantidade));

        atv4();
    }

    public static void atv4(){
        System.out.println();
        System.out.println("Atividade 04");
        List<Produto> produtos = getProdutos();
        List<Produto> produtosCaros = produtos.stream()
                                              .filter(p -> p.getPreco() > 100)   
                                              .collect(Collectors.toList());
        produtosCaros.forEach(p -> System.out.println(p.getNome() + "- R$" +p.getPreco()));            

        atv5();
    }

    public static void atv5(){
        System.out.println();
        System.out.println("Atividade 05");
        List<Produto> produtos = getProdutos();
        double somaTotal = produtos.stream()
                                   .mapToDouble(Produto :: getPreco)
                                   .sum();
        System.out.println("Soma total dos produtos: " +somaTotal);

        atv6();

    }

    public static void atv6(){
        System.out.println();
        System.out.println("Atividade 06");
        List <String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List <String> tamanho = linguagens.stream()
                                          .sorted(Comparator.comparingInt(String :: length))
                                          .collect(Collectors.toList());
       System.out.println("Ordenadas por tamanho: "+tamanho);                                   
    }

    public static List<Produto> getProdutos(){
        return Arrays.asList(
            new Produto("Camiseta", 70.00),
            new Produto("Calça", 140.00),
            new Produto("Tênis", 120.00),
            new Produto("Saia", 90.00)
        );
    }
}

class Produto{
    
    private String nome;
    private double preco;

    public Produto(String nome, double preco){
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