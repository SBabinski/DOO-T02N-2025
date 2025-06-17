package Aula11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        System.out.println("--- Atvd 1 ---");
        List<Integer> numeros = new ArrayList<>(List.of(4,5,7,10,9,23,1,2));

        Stream<Integer> numerosStream = numeros.stream()
                .filter(n -> n % 2 == 0);

        numerosStream.forEach(System.out::println);
        System.out.println("------------------------------");

        System.out.println("--- Atvd 2 ---");

        List<String> nomes = new ArrayList<>(List.of("roberto", "josé", "caio", "vinicius"));

        Stream<String> nomesStream = nomes.stream()
                .map(String::toUpperCase);

        nomesStream.forEach(System.out::println);
        System.out.println("------------------------------");

        System.out.println("--- Atvd 3 ---");

        List<String> palavras = new ArrayList<>(List.of("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado"));

        palavras.stream()
                .distinct()
                .forEach(palavra -> {
                    long count = palavras.stream()
                            .filter(i -> i.equals(palavra))
                            .count();
                    System.out.println(palavra + ": " + count);
                 });

        System.out.println("------------------------------");

        System.out.println("--- Atvd 4 ---");

        List<Produto> produtos = List.of(
                new Produto("agua", 3.0),
                new Produto("cafe", 200.0),
                new Produto("coca", 6.5),
                new Produto("energerico", 150.0));

        Stream<Produto> streamProdutos = produtos.stream()
                .filter(i -> i.getPreco() > 100.0);

        streamProdutos.forEach(Produto::print);

        System.out.println("------------------------------");

        System.out.println("--- Atvd 4 ---");

        Double somaProdutos = produtos.stream()
                .mapToDouble(produto -> produto.getPreco())
                .sum();

        System.out.println(somaProdutos);

        System.out.println("------------------------------");

        List<String> lista = List.of("Java", "Python", "C", "JavaScript", "Ruby");

        Stream<String> listaOrdenada = lista.stream()
                .sorted(Comparator.comparingInt(String::length));

        listaOrdenada.forEach(System.out::println);
        System.out.println("------------------------------");
    }


}
