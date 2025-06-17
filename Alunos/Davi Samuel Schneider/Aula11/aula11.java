import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class aula11 {
    public static void main(String[] args) {
        //ATV1
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        System.out.println(integers.stream().filter((i) -> i % 2 == 0).toList());

        //ATV2
        List<String> strings = new ArrayList<>(List.of("roberto", "josé", "caio", "vinicius"));
        System.out.println(strings.stream().map(String::toUpperCase).toList());

        //ATV3
        List<String> palavras = new ArrayList<>(List.of("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado"));
        System.out.println(palavras.stream().filter((s) -> s.equals("se")).count());


        //ATV4
        List<Produto> produtos = new ArrayList<>(List.of(
                new Produto("Batata", 100D),
                new Produto("Carne", 200D),
                new Produto("Arroz", 30D),
                new Produto("Bala", 1D)));

        System.out.println(produtos.stream().filter((produto) -> produto.getPrice() >= 100).toList());


        //ATV5
        System.out.println(produtos.stream().map((p) -> p.getPrice()).reduce((p1, p2) -> p1 + p2));

        //ATV6
        List<String> list = new ArrayList<>(List.of("Java", "Python", "C", "JavaScript", "Ruby"));
        System.out.println(list.stream().sorted((s1, s2) -> Integer.compare(s1.length(), s2.length())).toList());

    }
}

class Produto {
    private String name;
    private Double price;

    public Produto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
