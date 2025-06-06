import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        //ATV1
        System.out.println("--- ATV1 ---");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers = numbers.stream()
                                            .filter(n -> n % 2 == 0)
                                            .collect(Collectors.toList());
        System.out.println("Números pares: " + evenNumbers);

        //ATV2
        System.out.println("\n--- ATV2 ---");
        List<String> names = Arrays.asList("roberto", "josé", "caio", "vinicius");
        List<String> upperCaseNames = names.stream()
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList());
        System.out.println("Nomes em maiúsculas: " + upperCaseNames);

        //ATV3
        System.out.println("\n--- ATV3 ---");
        List<String> words = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
        Map<String, Long> wordCounts = words.stream()
                                            .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        System.out.println("Contagem de palavras: " + wordCounts);

        //ATV4
        System.out.println("\n--- ATV4 ---");
        List<Produto> products = Arrays.asList(
            new Produto("Notebook", 1200.00),
            new Produto("Mouse", 75.50),
            new Produto("Teclado Mecânico", 150.00),
            new Produto("Monitor", 950.00)
        );

        List<Produto> expensiveProducts = products.stream()
                                                .filter(p -> p.getPrice() > 100.00)
                                                .collect(Collectors.toList());
        System.out.println("Produtos com preço maior que R$ 100,00:");
        expensiveProducts.forEach(System.out::println);

        //ATV5
        System.out.println("\n--- ATV5 ---");
        double totalPrice = products.stream()
                                    .mapToDouble(Produto::getPrice)
                                    .sum();
        System.out.println("Soma total dos preços dos produtos: R$ " + String.format("%.2f", totalPrice));

        //ATV6
        System.out.println("\n--- ATV6 ---");
        List<String> languages = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> sortedLanguages = languages.stream()
                                                .sorted((s1, s2) -> Integer.compare(s1.length(), s2.length()))
                                                .collect(Collectors.toList());
        System.out.println("Linguagens ordenadas pelo tamanho: " + sortedLanguages);
    }
}

// Classe Produto para ATV4 e ATV5
class Produto {
    private String name;
    private double price;

    public Produto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Produto{" +
               "nome='" + name + '\'' +
               ", preço=" + String.format("%.2f", price) +
               '}';
    }
}