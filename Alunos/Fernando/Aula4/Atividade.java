package Aula4;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Atividade {


    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        ListaRegistros listaRegistros = new ListaRegistros();

        boolean continuar = true;
        while (continuar == true) {
            try {
                menu();
                int opcao = scan.nextInt();
                switch (opcao) {
                    case 1: 
                        listaRegistros.adicionarVenda();
                        break;
                    case 2:
                        listaRegistros.calcularTroco();
                        break;
                    case 3:
                        System.out.println("Escolha uma opcao:");
                        System.out.println("[1] Listar todos os registros");
                        System.out.println("[2] Buscar por data");
                        opcao = scan.nextInt();
                        scan.nextLine(); 
                        if (opcao == 1) {
                            listaRegistros.listarRegistros();
                        } else if (opcao == 2) {
                            listaRegistros.buscarPorData();
                        } else {
                            System.out.println("Opcao invalida.");
                        }
                        break;
                    case 4:
                        continuar = false;
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida.");
	            scan.nextLine();
            }
        }

        scan.close();
    }

    static void menu() {
        System.out.println("Digite uma das opcoes:");
        System.out.println("[1] Calcular Preco Total");
        System.out.println("[2] Calcular Troco");
        System.out.println("[3] Listar Registros");
        System.out.println("[4] Sair");
        return;
    }

}
