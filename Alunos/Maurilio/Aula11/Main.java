import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        atividade01();
        System.out.println();
        atividade02();
        System.out.println();
        atividade03();
        System.out.println();
        atividade04();
        System.out.println();
        atividade05();
        System.out.println();
        atividade06();
    }

    public static void atividade01() {
        List<Integer> numeroList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        numeroList.stream()
                .filter(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));
    }

    public static void atividade02() {
        List<String> nomesList = new ArrayList<>(List.of("roberto", "josé", "caio", "vinicius"));
        nomesList.stream()
                .map(nome -> nome.toUpperCase())
                .forEach(nome -> System.out.print(nome + " "));
    }

    public static void atividade03() {
        List<String> palavras = new ArrayList<>(List.of("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado"));

        Map<String, Long> contagem = palavras.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        contagem.forEach((palavra, quantidade) ->
                System.out.print(palavra + "= " + quantidade + " / "));
    }

    public static class Produto {
        private String nome;
        private Double preco;
        static List<Produto> produtoList = new ArrayList<>(List.of(
                new Produto("Abacate", 15.0),
                new Produto("Notebook", 3500.69),
                new Produto("Gin", 325.99),
                new Produto("Café", 30.50)
        ));

        public Produto() {
        }

        public Produto(String nome, Double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Double getPreco() {
            return preco;
        }

        public void setPreco(Double preco) {
            this.preco = preco;
        }
    }

    public static void atividade04() {
        List<Produto> produtosCaros = Produto.produtoList.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("Produtos com valor acima de R$100,00:");

        produtosCaros.forEach(produto ->
                System.out.print(produto.getNome() + ": R$" + produto.getPreco() + " / "));
    }

    public static void atividade05() {
        double soma = Produto.produtoList.stream()
                .map(produto -> produto.getPreco())
                .reduce(0.0, (subtotal, preco) -> subtotal + preco);

        System.out.print(String.format("\n O valor total dos produtos é: R$%.2f", soma));
    }

    public static void atividade06() {
        List<String> listLinguagens = new ArrayList<>(List.of("Java", "Python", "C", "JavaScript", "Ruby"));

        List<String> listLinguagensOrdenadas = listLinguagens.stream()
                .sorted(Comparator.comparing(palavra -> palavra.length()))
                .toList();

        listLinguagensOrdenadas.forEach(System.out::println);
    }

}