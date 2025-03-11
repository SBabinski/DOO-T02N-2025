import java.util.Scanner;

public class Aula02 {  
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
       int opc;
       while(true){
        System.out.println("Bem vindo a Calculadora!!!");
        System.out.println("[1] Calcular Preço Total");
        System.out.println("[2] Calcular Troco");
        System.out.println("[3] Sair");
        opc = scan.nextInt();


        switch(opc){
        case 1:  PreçoTotal();
        break;
        case 2: Troco();
        break;
        case 3: System.out.println("saindo...");
        return;
        default: System.out.println("Opção inválida");
        }
    }
    }
    public static void PreçoTotal(){
        System.out.println("Insira a quantidade de Planta:\n");
        int a = scan.nextInt();
        System.out.println("Insira o valor de venda da Planta:\n");
        int b = scan.nextInt();
        int valor = a*b;
        System.out.printf("O valor da venda ficou em: %d\n ", valor);
    }
    public static void Troco(){
        System.out.println("Insira o valor recebido do Cliente:\n");
        int x = scan.nextInt();
        System.out.println("Insira o valor total da compra:\n");
        int y = scan.nextInt();
        int troco = x-y;
        if(x<y){
            System.out.println("saldo insuficiente");
        } else {
            System.out.printf("O valor do troco é de: %d", troco);
        }


    }
   
           
        }
   

