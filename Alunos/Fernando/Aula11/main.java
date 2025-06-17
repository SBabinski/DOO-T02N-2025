package Aula11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class main {
    
    public static void main(String[] args) {
        
        atividade1();
        atividade2();
        atividade3();
        atividade4();
        atividade5();
        atividade6();  
    }

    static void atividade1() {

        List<Integer> numeros = new ArrayList<>();
        numeros.add(1);
        numeros.add(2);
        numeros.add(3);
        numeros.add(4);
        numeros.add(5);
        numeros.add(6);
        numeros.add(7);
        numeros.add(8);
        numeros.add(9);
        numeros.add(10);

        List<Integer> pares = numeros.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());

        System.out.println("Números pares: " + pares + "\n");
    }

    static void atividade2() {
        
        List<String> nomes = new ArrayList<>();
        nomes.add("roberto");
        nomes.add("josé");
        nomes.add("caio");
        nomes.add("vinicius");

        List<String> maiusculas = nomes.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        System.out.println("Nomes em maiúsculas: " + maiusculas +"\n");    
    }

    static void atividade3() {
        List<String> palavras = new ArrayList<>();
        palavras.add("se");
        palavras.add("talvez");
        palavras.add("hoje");
        palavras.add("sábado");
        palavras.add("se");
        palavras.add("quarta");
        palavras.add("sábado");

        List<String> unicas = palavras.stream()
            .filter(p -> palavras.stream().filter(p::equals).count() == 1)
            .collect(Collectors.toList());

        System.out.println("Quantidade de palavras unicas: " + unicas.size() + "\n");
    }
    
    static void atividade4() {
        
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Mouse", 50.0));
        produtos.add(new Produto("Teclado", 100.0));
        produtos.add(new Produto("Monitor", 500.0));
        produtos.add(new Produto("Impressora", 300.0));

        System.out.println("Produtos com preço maior que R$100,00:");
        List<Produto> produtosCaros = produtos.stream()
            .filter(p -> p.getPreco() > 100.0)
            .peek(p -> System.out.println(p.getNome() + " - R$" + p.getPreco()))
            .collect(Collectors.toList());

    }

    static void atividade5() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Mouse", 50.0));
        produtos.add(new Produto("Teclado", 100.0));
        produtos.add(new Produto("Monitor", 500.0));
        produtos.add(new Produto("Impressora", 300.0));

        List<Produto> produtosCaros = produtos.stream()
            .filter(p -> p.getPreco() > 100.0)
            .collect(Collectors.toList());
        
        double soma = produtosCaros.stream()
            .mapToDouble(Produto::getPreco)
            .sum();

        System.out.println("Soma dos preços dos produtos com preço maior que R$100,00: R$" + soma +"\n");
    }

    static void atividade6() {
        List<String> linguagens = new ArrayList<>();
        linguagens.add("Java");
        linguagens.add("Python");
        linguagens.add("JavaScript");
        linguagens.add("C++");
        linguagens.add("Ruby");

        System.out.println("Palavras ordenadas por tamanho:");
        List<String> ordemTamanho = linguagens.stream()
            .sorted(Comparator.comparing(String::length))
            .peek(System.out::println)
            .toList();
    }

}
