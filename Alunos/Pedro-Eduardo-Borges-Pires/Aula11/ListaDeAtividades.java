package aula11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaDeAtividades {

	public static void main(String[] args) {
		// atividade1();
		// atividade2();
		// atividade3();
		// atividade4();
		// atividade5();
		// atividade6();
	}

	public static void atividade1() {
		List<Integer> lista = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

		List<Integer> pares = lista.stream().filter(num -> num % 2 == 0).toList();

		System.out.println(pares);

	}

	public static void atividade2() {
		List<String> lista = new ArrayList<>(Arrays.asList("roberto", "josé", "caio", "vinicius"));

		List<String> upperCase = lista.stream().map(s -> s.toUpperCase()).toList();
		System.out.println(upperCase);
	}

	public static void atividade3() {
		List<String> lista = new ArrayList<>(Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado"));

		Long valor = lista.stream().filter(s -> s.equals(s)).count();
		System.out.println(valor);
	}

	public static void atividade4() {

		List<Produto> produtos = new ArrayList<>(Arrays.asList(new Produto("Computador", 900.0),
				new Produto("Telefone", 50.0), new Produto("Playstation 5", 500.0), new Produto("Geladeira", 90.50)));

		List<Produto> baratos = produtos.stream().filter(p -> (p.getPreco() > 100.0)).toList();
		System.out.println(baratos.toString());

	}

	public static void atividade5() {

		List<Produto> produtos = new ArrayList<>(Arrays.asList(new Produto("Computador", 900.0),
				new Produto("Telefone", 50.0), new Produto("Playstation 5", 500.0), new Produto("Geladeira", 90.50)));

		System.out.println(produtos.stream().map(p -> p.getPreco()).reduce((p1, p2) -> p1 + p2));

	}

	public static void atividade6() {
		List<String> lista = new ArrayList<>(Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby"));

		System.out.println(lista.stream().sorted((p1, p2) -> Integer.compare(p1.length(), p2.length())).toList());
	}

}

class Produto {
	private String nome;
	private Double preco;

	public Produto(String nome, Double preco) {
		this.nome = nome;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public Double getPreco() {
		return preco;
	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", preco=" + preco + "]";
	}

}
