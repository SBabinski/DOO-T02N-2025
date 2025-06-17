import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args){
        exercicio1();
        exercicio2();
        exercicio3();
        exercicio4();
        exercicio6();
    }

    public static void exercicio1(){
        System.out.println("\nEXERCÍCIO 1: ");
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);

        List<Integer> evenWords = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Lista dos números normais: " + numbers);
        System.out.println("Lista dos números pares: " + evenWords);
    }

    public static void exercicio2(){
        System.out.println("\nEXERCÍCIO 2: ");
        List<String> listNames = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> listUpperCase = listNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Lista dos nomes normais: " + listNames);
        System.out.println("Lista dos nomes em UpperCase: " + listUpperCase);
    }

    public static void exercicio3(){
        System.out.println("\nEXERCÍCIO 3: ");
        List<String> words = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Long uniqueWords = words.stream()
                .distinct() //remove duplicatas
                .count();

        System.out.println("Todas as palavras: " + words);
        System.out.println("Plavras únicas: " + uniqueWords);

    }

    static class Produto{
        String nome;
        double preco;

        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public double getPreco() {
            return preco;
        }

        @Override
        public String toString() {
            return nome +
                    " = " + preco;
        }
    }
    public static void exercicio4() {
        System.out.println("\nEXERCÍCIO 4: ");
        List<Produto> listaDeProdutos = Arrays.asList(
                new Produto("Tv", 3400),
                new Produto("Microondas", 2100),
                new Produto("Maçaneta", 34),
                new Produto("Rosca autocortante", 8));

        List<Produto> productsLargerThan100 = listaDeProdutos.stream()
                .filter(p -> p.getPreco() > 100)
                .collect(Collectors.toList());

        System.out.println("Lista dos produto normal: " + listaDeProdutos);
        System.out.println("Lista dos produtos maiores que 100: " + productsLargerThan100);

        System.out.println("\nEXERCÍCIO 5: ");
        double productsSum = listaDeProdutos.stream()
                .mapToDouble(p -> p.getPreco())
                .sum();

        System.out.println("Soma de todos os produtos: " + productsSum);
    }

    public static void exercicio6(){

        List<String> list = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");


        List<String> ordenedList = list.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("Lista normal: " + list);
        System.out.println("Lista ordenada: " + ordenedList);

    }


}
