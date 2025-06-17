package Alunos.FelipeHeringer.Aula11;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // ATV1

        System.out.println("Atividade 1 - Filtrando números pares de um ArrayList\n");
        int[] numerosArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17,18,19,20};
        ArrayList<Integer> numeros = new ArrayList<>();

        for (int numero : numerosArray) {
            numeros.add(numero);
        }

        ArrayList<Integer> numerosPares = numeros.stream()
                        .filter(num -> num % 2 == 0)
                        .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Números pares: " + numerosPares);
        
        System.out.println("----------------------------------");

        // ATV2

        System.out.println("Atividade 2 - Convertendo nomes para maiúsculas\n");
        ArrayList<String> nomes = new ArrayList<>();
        nomes.add("roberto");
        nomes.add("josé");
        nomes.add("caio");
        nomes.add("vinicius");

        nomes.stream()
                .map(nome-> nome.toUpperCase())
                .forEach(nome -> System.out.println("Nome formatado: " + nome));
        System.out.println("----------------------------------");

        // ATV3

        System.out.println("Atividade 3 - Contando o numero de vezes de palavras unicas em um ArrayList\n");
        ArrayList<String> palavras = new ArrayList<>();
        palavras.add("se");
        palavras.add("talvez");
        palavras.add("hoje");
        palavras.add("sábado");
        palavras.add("se");
        palavras.add("quarta");
        palavras.add("sábado");

        System.out.println("Palavras únicas e suas contagens:");
    
        palavras.stream()
                .distinct()
                .forEach(palavra -> {
                    long count  = palavras.stream()
                            .filter(p -> p.equals(palavra))
                            .count();
                    System.out.println("\tPalavra: " + palavra + ", Contagem: " + count);
                });     

        System.out.println("----------------------------------");

        // ATV4

        System.out.println("Atividade 4 - Exibindo produtos com preço acima de R$100\n");

        class Produto {
            private String nome;
            private Double preco;

            Produto(String nome, Double preco) {
                this.nome = nome;
                this.preco = preco;
            }

            public void exibir() {
                System.out.println("\tProduto: " + nome + ", Preço: R$" + preco);
            }

            public Double getPreco() {
                return preco;
            }
        }

        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Produto A", 50.0));
        produtos.add(new Produto("Produto B", 150.0));
        produtos.add(new Produto("Produto C", 200.0));
        produtos.add(new Produto("Produto D", 80.0));

        System.out.println("Produtos com preço acima de R$100:");

        produtos.stream()
                .filter(produto -> produto.getPreco() > 100)
                .forEach(produto -> produto.exibir());
        
        System.out.println("----------------------------------");

        // ATV5

        System.out.println("Atividade 5 - Exibindo a soma dos produtos da atividade 4\n");

        Double somaPrecos = produtos.stream()
                .filter(produto -> produto.getPreco() > 100)
                .mapToDouble(Produto::getPreco)
                .sum();
        
        System.out.println("Soma dos preços dos produtos acima de R$100: R$" + somaPrecos);
        System.out.println("----------------------------------");

        // ATV6

        System.out.println("Atividade 6 - Ordenando palavras por tamanho\n");
        ArrayList<String> lingProgramacao = new ArrayList<>();
        lingProgramacao.add("Java");
        lingProgramacao.add("Python");
        lingProgramacao.add("C");
        lingProgramacao.add("JavaScript");
        lingProgramacao.add("Ruby");

        System.out.println("Palavras antes da ordenação: " + lingProgramacao);

        ArrayList<String> palavrasOrdenadas = lingProgramacao.stream()
                .sorted((p1, p2) -> Integer.compare(p1.length(), p2.length()))
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Palavras ordenadas por tamanho: " + palavrasOrdenadas);

        System.out.println();
        scanner.close();
    }
    
}
