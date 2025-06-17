import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        
        //ATV 01
        System.out.println("\n==== Exercício 01 ====");
        ArrayList<Integer> numeros = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        
        List<Integer> pares = numeros.stream()
                                    .filter(n -> n %2 == 0)
                                    .collect(Collectors.toList());
        System.out.println("Numeros pares: " + pares);
        
        //ATV02
        System.out.println("\n==== Exercício 02 ====");
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("roberto", "jose", "caio", "vinicius"));

        List<String> maisculas = nomes.stream()
                                    .map(s -> s.toUpperCase())
                                    .collect(Collectors.toList());
        System.out.println("Nomes em maiusculas:" + maisculas);

        //ATV 03
        System.out.println("\n==== Exercício 03 ====");
        ArrayList<String> palavras = new ArrayList<>(Arrays.asList("se", "talvez","hoje","sabado","se","quarta","sabado"));

        Map<String,Integer> repeticoes = palavras.stream()
                                                .collect(Collectors.groupingBy(p -> p, Collectors.summingInt(p -> 1)));
        System.out.println("Contagem de palavras: " + repeticoes); 

        //ATV 04
        System.out.println("\n==== Exercício 04 ====");
        ArrayList<Produto> produtos = new ArrayList<>();
        Produto produtoA = new Produto("Base para o rosto", 145);
        Produto produtoB = new Produto("Mascara de cilios", 50);
        Produto produtoC = new Produto("Blush", 110);
        Produto produtoD = new Produto("Sombra", 85);
        produtos.add(produtoA);
        produtos.add(produtoB);
        produtos.add(produtoC);
        produtos.add(produtoD);

        List<Produto> precosProdutos = produtos.stream()
                                                .filter(p -> p.getPreco() > 100)
                                                .collect(Collectors.toList());
        System.out.println("Produtos com preços maiores que R$100: ");
        precosProdutos.forEach(p -> System.out.println(p));

        //ATV 05
        System.out.println("\n==== Exercício 05 ====");
        float somaTotal = produtos.stream()
                                        .map(p -> p.getPreco())
                                        .reduce(0.0f, (a, b) -> a + b);
        System.out.println("Valor total dos produtos: R$" + somaTotal);

        //ATV 06
        System.out.println("\n==== Exercício 06 ====");
        ArrayList<String> linguagens = new ArrayList<>(Arrays.asList("Java","Python","C","JavaScript","Ruby"));
        
        List<String> ordenar = linguagens.stream()
                                        .sorted((s1, s2) -> Integer.compare(s1.length(), s2.length()))
                                        .collect(Collectors.toList()); 
        System.out.println("Lista ordenada por tamanho crescente:");
        ordenar.forEach(s -> System.out.println(s));

    }
}

//ATV 04
class Produto {
    String nome; float preco;

    public Produto(String nome, float preco){
        this.nome = nome;
        this.preco = preco;
    }
    public float getPreco() {
        return preco;  
    }
    public String toString() {
        return "Produto: " + nome + " | Preço: R$" + preco;
    }
}