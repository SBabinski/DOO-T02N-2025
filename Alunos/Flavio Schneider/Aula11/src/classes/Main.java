package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import classes.Produto;

public class Main {

	public static List<Produto> listaProdutos = Arrays.asList(
			new Produto("notbook", 3500),
			new Produto("mouse", 80),
			new Produto("cadeira", 500),
			new Produto("fone", 90));
	
	public static void main(String[] args) {
		
		atv1();
		atv2();
		atv3();
		atv4();
		atv5();
		atv6();

		
	}
	
	public static void atv1() {
		
		
		List<Integer> numeros = new ArrayList<>();
		
		numeros.add(2);
		numeros.add(3);
		numeros.add(4);
		numeros.add(5);
		numeros.add(6);
		numeros.add(7);
		numeros.add(8);
		
		numeros.stream().filter(numero -> numero % 2 == 0).forEach(System.out::println);
		
	}
	
	public static void atv2() {
		
		List<String> nomes = new ArrayList<String>();
		
		nomes.add("roberto");
		nomes.add("jose");
		nomes.add("caio");
		nomes.add("vinicius");
	
		nomes.stream().map(String::toUpperCase).forEach(System.out::println);
		
	}
	
	public static void atv3() {
		
		List<String> palavras = new ArrayList<String>();
		
		
		palavras.add("se");
		palavras.add("se");
		palavras.add("sabado");
		palavras.add("sabado");
		palavras.add("quarta");
		palavras.add("talvez");
		palavras.add("hoje");
		
		Map<Object, Long> contagem = palavras.stream().collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));
		
		
		 contagem.forEach((palavra, quantidade) ->
         System.out.println(palavra + ": " + quantidade));
	}
	
	
	
	public static void atv4() {
		
//		List<Produto> listaProdutos = Arrays.asList(
//				new Produto("notbook", 3500),
//				new Produto("mouse", 80),
//				new Produto("cadeira", 500),
//				new Produto("fone", 90));
		

//		for (Produto produto : listaProdutos) {
//			
//			System.out.println(produto.getNome());
//			
//		}
		
		
		List<Produto> produtosCaros = listaProdutos.stream().filter(p -> p.getPreco() > 100).collect(Collectors.toList());
		
		produtosCaros.forEach(System.out::println);
		
	}
	
	public static void atv5() {
		
		double total = listaProdutos.stream().mapToDouble(Produto::getPreco).sum();
		
		System.out.println("soma total: " + total);
		
		
		
	}
	
	public static void atv6() {
		
		 List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

	        List<String> ordenadasPorTamanho = linguagens.stream()
	            .sorted(Comparator.comparingInt(String::length)) 
	            .collect(Collectors.toList());

	        System.out.println("Ordenadas por tamanho:");
	        ordenadasPorTamanho.forEach(System.out::println);
		
		
		
	}

}
