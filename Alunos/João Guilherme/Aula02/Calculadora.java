import java.util.Scanner;

public class Aula02 {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int opcao;
    int total = 0;

    while (true) {
      System.out.println("[1] - Calcular Preço Total\r\n" + //
      "[2] - Calcular Troco    \r\n" + //
      "[3] - Sair    ");
      System.out.print("Escolha a opcao: ");
      opcao = scan.nextInt();

      switch (opcao) {
        case 1:
        int qtd;
        int valor;
    
        System.out.println("Qual o valor das plantas?");;
        valor = scan.nextInt();
    
        System.out.println("Qual a quantidade comprada?");
        qtd = scan.nextInt();
    
        total = valor*qtd;
  
        System.out.printf("O valor total foi de R$%d\n",total);
      break;

      case 2:   
      int cliente;
  
      System.err.println("Qual o valor que o cliente deu?");
      cliente = scan.nextInt();
  
      int troco = cliente - total;
      if (troco>0) {
        System.out.printf("Entao o troco foi de R$%d\n", (troco));
      }else if (troco == 0) {
        System.out.printf("O valor entregue foi exato ao valor total de compra\n");
      }else{System.out.println("Caloteiro");}
      break;

      case 3:
      System.out.println("Saindo...");
      scan.close();
      return;

      default:
        System.out.println("Opcao invalida!!!!"); 
      }
    }
  } 
}
