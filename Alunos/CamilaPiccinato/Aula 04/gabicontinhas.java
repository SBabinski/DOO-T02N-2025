import java.util.Scanner;
import java.util.ArrayList;

public class gabicontinhas{
    public static void main(String[] args) {
    int op=0, quantPlanta=0, dia=0, mes=0;
    float valorPlanta=0, total=0, dinheiroCliente=0, totalDesconto = 0, desconto = 0;
    int vendas[][] = new int[12][31];

    ArrayList<Float> valoresPlantas = new ArrayList<>();
    ArrayList<Integer> quantidadesPlantas = new ArrayList<>();
    ArrayList<Float> descontoCliente = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);
    while(op != 5){
    System.out.println("\n\nMENU DE CONTAS \n[1]Calcular preço total \n[2]Calcular troco \n[3]Relatório de vendas \n[4]Consultar vendas do dia escolhido \n[5]Sair");
    op = scanner.nextInt();
    
    if (op == 1){
        System.out.println("Insira o valor da planta");
        valorPlanta = scanner.nextFloat();

        System.out.println("Insira a quantidade de plantas");
        quantPlanta = scanner.nextInt();
        quantidadesPlantas.add(quantPlanta);

        if(quantPlanta >= 10){
            System.out.println("O cliente recebeu 5% de desconto");
            total= valorPlanta * quantPlanta;
            desconto = total * 0.95f;
            totalDesconto = total - desconto;
            descontoCliente.add(totalDesconto);

            total = desconto;
        }
        else{
            total = valorPlanta * quantPlanta;
        }
        System.out.printf("O total é de: %.2f", total );
        valoresPlantas.add(total);
    }

    if (op==2){
        System.out.println("Insira o valor dado pelo cliente");
        dinheiroCliente = scanner.nextFloat();

        System.out.printf("O troco do cliente deve ser de : %.2f", dinheiroCliente - total);
    }
    
    if (op==3){   
        
        int somaQuant = 0;
        float somaValor = 0;
        float somaDescontos = 0;

        for (int quantidade : quantidadesPlantas) {
        somaQuant += quantidade;
    }

        System.out.println("A quantidade de plantas vendidas: " + somaQuant);
    
        for(Float quantidade : valoresPlantas) {
            somaValor += quantidade;
        }

        System.out.println("O total do valor recebido: R$" + somaValor);
        
        for(float quantidade : descontoCliente){
            somaDescontos += quantidade;
        }

        System.out.println("Total de desconto dado: R$" + somaDescontos);
    }
    if (op==4){
        System.out.println("Insira o dia: ");
        dia = scanner.nextInt();

        System.out.println("Insira o mes");
        mes = scanner.nextInt();

        vendas[mes - 1][dia - 1] += quantPlanta;

        System.out.println("Total de vendas em " + dia + "/" + mes + ": " + vendas[mes - 1][dia - 1] + " plantas");
        
     }
    }
    }
    }