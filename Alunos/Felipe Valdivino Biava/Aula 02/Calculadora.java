import java.util.Scanner;

class Calculadora {

    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        do{
            System.out.println(" [1] - Calcular Preço Total / [2] - Calcular Troco / [3] - Sair ");
            op = scan.nextInt();

            switch(op){
                case 1:
                    precoTotal();break;
                case 2:
                    calcularTroco();break;
                case 3:
                    break;
            }
        }while(op != 3);
    }

    public static void precoTotal(){
        System.out.println("Entre com a quantidade");
        int quantidade = scan.nextInt();

        System.out.println("Entre com o valor: ");
        float valor = scan.nextFloat();

        System.out.println("Preço total: " + quantidade * valor);
    }

    public static void calcularTroco(){
        System.out.println("Entre com o valor a ser dado pelo cliente: ");
        float valorDado = scan.nextFloat();

        System.out.println("Entre com o valor total da compra: ");
        float valorCompra = scan.nextFloat();

        float resultado = valorDado - valorCompra;

        System.out.println("O valor do troco é: " + resultado);

    }

}


