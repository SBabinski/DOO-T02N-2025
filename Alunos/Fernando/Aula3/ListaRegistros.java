package Aula3;

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
        System.out.println("O preco total e: " + calculadora.calcularPrecoTotal(quantidade, preco));
        total = calculadora.calcularPrecoTotal(quantidade, preco);
        boolean comDesconto;
        if (quantidade >= 10) {
            comDesconto = true;
        } else {
            comDesconto = false;
        }
        RegistroVendas registro = new RegistroVendas(quantidade, preco, total, comDesconto);
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

}