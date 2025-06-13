package aula11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Api {
	
	  private String nome;
	    private double preco;

	    public Api(String nome, double preco) {
	        this.nome = nome;
	        this.preco = preco;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public double getPreco() {
	        return preco;
	    }

	    @Override
	    public String toString() {
	        return nome + " - R$ " + preco;
	    }

	public static void main(String[] args) {
		atv1();
		atv2();
		atv3();
		atv4();
		atv5();
		atv6();
	}
	
	public static void atv1() {
        List<Integer> numeros = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println("Atv1 - Números pares: " + pares);
    }
	 public static void atv2() {
	        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
	        List<String> nomesMaiusculos = nomes.stream()
	                                            .map(String::toUpperCase)
	                                            .collect(Collectors.toList());
	        System.out.println("Atv2 - Nomes em maiúsculas: " + nomesMaiusculos);
	    }
	  public static void atv3() {
	        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
	        Map<String, Long> contagem = palavras.stream()
	                                             .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	        System.out.println("Atv3 - Contagem de palavras: " + contagem);
	    }
	  public static void atv4() {
		  
	        List<Api> produtos = criarProdutos();
	        List<Api> produtosCaros = produtos.stream()
	                                              .filter(p -> p.getPreco() > 100.0)
	                                              .collect(Collectors.toList());
	        System.out.println("Atv4 - Produtos com preço > R$100: " + produtosCaros);
	    }
	  public static void atv5() {
	        List<Api> produtos = criarProdutos();
	        double somaTotal = produtos.stream()
	                                   .mapToDouble(Api::getPreco)
	                                   .sum();
	        System.out.println("Atv5 - Soma total dos produtos: R$ " + somaTotal);
	    }
	  public static void atv6() {
	        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
	        List<String> ordenadas = linguagens.stream()
	                                           .sorted(Comparator.comparingInt(String::length))
	                                           .collect(Collectors.toList());
	        System.out.println("Atv6 - Linguagens ordenadas por tamanho: " + ordenadas);
	    }
	  
	  public static List<Api> criarProdutos() {
	        return Arrays.asList(
	            new Api("Monitor", 750.0),
	            new Api("Mouse", 50.0),
	            new Api("Teclado", 120.0),
	            new Api("Caixa de som", 90.0));
	  }
	       

}

