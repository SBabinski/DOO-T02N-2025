import java.util.*; 
import java.util.stream.*;

class Produto { 
    String nome;
   Integer preco;

    public Produto(String nome, Integer preco) {
        this.nome = nome;
        this.preco = preco;
    }
    public String getNome() {
        return nome;
    }

    public Integer getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Produto [nome=" + nome + ", preco=" + preco + "]";
    }

    
    
}
public class Main {
    public static void main(String[] args) {
        Atv6();
            }
        public static void  Atv1() {

        List<Integer>  num = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);

        List<Integer> pares = num.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());

        System.out.println(pares);

        }

        public static void Atv2() { 
            List<String> nomes = Arrays.asList("roberto", "josé", "carlos", "vinicius");

            List<String> Maiscula = nomes.stream().map(String::toUpperCase).collect(Collectors.toList());

            System.out.println(Maiscula);
        }

        public static void Atv3() { 
             List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

             Map<String, Long> contar = palavras.stream().collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

             contar.forEach((palavra, quantidade)->  System.out.println(palavra + " : "  + quantidade));
        }

        public static void Atv4(){ 
            List<Produto> produtos = Arrays.asList(
                new Produto("ESPADA LONGA AMALDIÇOADA", 100),
                new Produto("PRODUTO MEGA FODA", 2),
                new Produto("PRODUTO NADA FODA", 2000),
                new Produto("CHINELO", 20)
            );

            List<Produto> Maior = produtos.stream().filter(preco -> preco.getPreco() > 100).collect(Collectors.toList());
            System.out.println(Maior);

        }

        public static void Atv5() {
                List<Produto> produtos = Arrays.asList(
                new Produto("ESPADA LONGA AMALDIÇOADA", 100),
                new Produto("PRODUTO MEGA FODA", 2),
                new Produto("PRODUTO NADA FODA", 2000),
                new Produto("CHINELO", 20)
            );
            
            int Soma = produtos.stream().mapToInt(preco -> preco.getPreco()).sum();
            System.out.println(Soma);
        }

        public static void Atv6() {
            List<String> Linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

            List<String> Comparacao = Linguagens.stream().sorted(Comparator.comparingInt(String :: length)).collect(Collectors.toList());

            System.out.println(Comparacao);
        }

        


    
}
