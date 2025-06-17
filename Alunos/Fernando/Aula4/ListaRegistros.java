package Aula4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaRegistros {

    Scanner scan = new Scanner(System.in);

    private List<RegistroVendas> listaRegistros;
    
    Calculadora calculadora = new Calculadora();
    double total = 0;

    public ListaRegistros() {
         listaRegistros = new ArrayList<>();
    }

    public void adicionarVenda() {
        System.out.println("Digite a quantidade da flor:");
        int quantidade = scan.nextInt();
        System.out.println("Digite o preco da flor:");
        double preco = scan.nextDouble();
        scan.nextLine(); 
        total = calculadora.calcularPrecoTotal(quantidade, preco);
        System.out.println("O preco total e: " + total);
        boolean comDesconto;
        if (quantidade >= 10) {
            comDesconto = true;
        } else {
            comDesconto = false;
        }
        LocalDateTime data = LocalDateTime.now();
        RegistroVendas registro = new RegistroVendas(quantidade, preco, total, comDesconto, data);
        listaRegistros.add(registro);
        System.out.println("Registro adicionado com sucesso!");
    }

    public void calcularTroco() {
        System.out.println("Digite o valor recebido: ");
        double valorRecebido = scan.nextDouble();
        System.out.println("O troco é: " + calculadora.calcularTroco(valorRecebido, total));
    }

    public void listarRegistros() {
        for (RegistroVendas registro : listaRegistros) {
            System.out.println(registro.toString());
        }
    }

    public void buscarPorData() {
        System.out.println("Escolha o tipo de busca:");
        System.out.println("[1] Buscar por dia e mês (formato: dd/MM)");
        System.out.println("[2] Buscar por mês (formato: MM)");
        int opcao = scan.nextInt();
        scan.nextLine(); 
        if (opcao == 1) {
            System.out.println("Digite o dia e mês no formato dd/MM:");
            String entrada = scan.nextLine();
            String[] partes = entrada.split("/");
            if (partes.length == 2) {
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                for (RegistroVendas registro : listaRegistros) {
                    LocalDateTime data = registro.getData();
                    if (data.getDayOfMonth() == dia && data.getMonthValue() == mes) {
                        System.out.println(registro.toString());
                    }
                }
            } else {
                System.out.println("Formato inválido. Use dd/MM.");
            }
        } else if (opcao == 2) {
            System.out.println("Digite o número do mês (MM):");
            String entrada = scan.nextLine();
            int mes = Integer.parseInt(entrada);
            for (RegistroVendas registro : listaRegistros) {
                LocalDateTime data = registro.getData();
                if (data.getMonthValue() == mes) {
                    System.out.println(registro.toString());
                }
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }
}