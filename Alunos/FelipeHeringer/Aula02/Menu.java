import java.util.Scanner;

public class Menu {
    private Calculadora calculadora;
    private Scanner scanner;

    public Menu(){
        this.calculadora = new Calculadora();
        this.scanner = new Scanner(System.in);
    }
    public void menuPrincipal(){
        System.out.println("--Menu da Loja de Plantas--");
        System.out.println("1-Calcular Preço Total");
        System.out.println("2-Calcular Troco");
        System.out.println("3-Sair");
        Integer opcao = this.scanner.nextInt();
        this.scanner.nextLine();
        opcoes(opcao);
    }
    private void opcoes(Integer opcaoSelecionada){
        switch (opcaoSelecionada){
            case 3 -> {
                return;
            }
            case 1 -> {
                System.out.println("Quantidade de Plantas ");
                Integer qtd = this.scanner.nextInt();
                this.scanner.nextLine();

                System.out.println("Preço da Planta ");
                Double preco = this.scanner.nextDouble();
                this.scanner.nextLine();
                Double precoTotal = calculadora.calculoPrecoTotal(qtd,preco);
                System.out.println("O preço total da compra foi de: "+precoTotal);
            }
            case 2 -> {
                System.out.println("Informe o valor Pago ");
                Double valorPago = this.scanner.nextDouble();
                this.scanner.nextLine();

                System.out.println("Informe o valor Total da Compra ");
                Double valorTotal = this.scanner.nextDouble();
                this.scanner.nextLine();

                Double Troco = calculadora.calculoTroco(valorPago,valorTotal);
                System.out.println("O troco é de: "+Troco);
            }
            default -> menuPrincipal();

        }
        if(opcaoSelecionada != 3) menuPrincipal();
    }
}