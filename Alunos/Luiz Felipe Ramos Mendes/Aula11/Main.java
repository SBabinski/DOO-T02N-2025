import org.w3c.dom.ls.LSOutput;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {

        //Atividade 1
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        list.stream().filter(x -> x % 2 == 0).forEach(System.out::println);

        //Atividade 2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> nomesMaiusculos = nomes.stream().map(String::toUpperCase).toList();

        System.out.println(nomesMaiusculos);

        //Atividade 3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> palavrasRep = palavras.stream().collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));
        System.out.println(palavrasRep);

        //Atividade 4

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Banana",99D));
        produtos.add(new Produto("maca",101D));
        produtos.add(new Produto("pera",200D));
        produtos.add(new Produto("abacate",100D));

        double precoMax= 100;

        List<Produto> produtoValor = produtos.stream().filter(produto -> produto.getPreco() > precoMax).toList();
        System.out.println(produtoValor.toString());

        //Atividade 5
        System.out.println(produtos.stream().map(produto -> produto.getPreco()).reduce((p1, p2) -> p1 + p2));

        //Atividade 6
        List<String> lista = new ArrayList<>(Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby"));
        System.out.println(lista.stream().sorted((l1, l2) -> Integer.compare(l1.length(), l2.length())).toList());


    }
}

class Produto {
    String nome;
    double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}