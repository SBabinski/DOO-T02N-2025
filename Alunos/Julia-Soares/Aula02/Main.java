package Aula02;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
     calcular();
    }
    
public static void calcular(){
    Calculos calculos = new Calculos();
    Scanner scanner = new Scanner(System.in);

    boolean executando = true;

    while (executando) {
        System.out.println("--Iniciando Calculadora--");
        System.out.println("1 - Calcular valor total da compra");
        System.out.println("2 - Mostrar troco à ser devolvido");
        System.out.println("3 - Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                calculos.calcularPrecoTotal();
                break;
            case 2:
                calculos.calcularTroco();
                break;
            case 3:
                executando = false;
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida, tente novamente!");
                break;

        }

    }
    }

}