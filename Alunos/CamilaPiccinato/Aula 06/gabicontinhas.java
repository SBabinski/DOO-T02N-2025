import java.util.Scanner;
import java.util.ArrayList;

class Cliente{
    String nome;
    String rua;
    String bairro;
    String cidade;
    int idade;
    
    public Cliente(String nome, String rua, String bairro, String cidade, int idade){
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.idade = idade;
    }
    void apresentarse(){
        System.out.println("\nNome: " + nome + "\nIdade: " + idade);
    }
}
class Vendedor {
    String nome;
    int idade;
    String loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, String loja, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new double[3];
        this.salarioRecebido[0] = salarioBase;
        this.salarioRecebido[1] = salarioBase * 1.1;
        this.salarioRecebido[2] = salarioBase * 0.95;
    }

    void apresentarse() {
        System.out.println("Nome: " + nome + "\nIdade: " + idade + "\nLoja: " + loja);
    }
    double calcularMedia() {
        double soma = 0;
        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }
        return soma / salarioRecebido.length;
    }
    double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;
    ArrayList<Vendedor> vendedores;
    ArrayList<Cliente> clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    int contarClientes() {
        return clientes.size();
    }
    
    int contarVendedores() {
        return vendedores.size();
    }
    void apresentarse() {
        System.out.println("Nome Fantasia: " + nomeFantasia +  "\nCNPJ: " + cnpj + "\nEndereço: " + rua + ", " + bairro + ", " + cidade);
    }
}
    

public class gabicontinhas{
    public static void main(String[] args) {
    int op=0, quantPlanta=0, dia=0, mes=0;
    float valorPlanta=0, total=0, dinheiroCliente=0, totalDesconto = 0, desconto = 0;
    int vendas[][] = new int[12][31];

    ArrayList<Float> valoresPlantas = new ArrayList<>();
    ArrayList<Integer> quantidadesPlantas = new ArrayList<>();
    ArrayList<Float> descontoCliente = new ArrayList<>();

    Loja myPlant = new Loja("My Plant", "My Plant LTDA", "0000", "Cascavel", "Centro", "Avenida Brasil");

    Cliente[] clientesArray = {
        new Cliente("Taylor", "Jorge Lacerda", "Centro", "Cascavel", 34),
        new Cliente("Camila", "JK", "Centro", "Cascavel", 19),
        new Cliente("Luan", "Toledo", "Centro", "Cascavel", 20)
    };
    for (Cliente cliente : clientesArray) {
        myPlant.clientes.add(cliente);
    }
    myPlant.vendedores.add(new Vendedor("João Silva", 25, "My Plant", "Cascavel", "Centro", "Rua das Flores", 2000.0));
    myPlant.vendedores.add(new Vendedor("Maria Santos", 30, "My Plant", "Cascavel", "Centro", "Avenida Principal", 2200.0));


    Scanner scanner = new Scanner(System.in);
    while(op != 6){
    System.out.println("\n\nMENU DE CONTAS \n[1]Calcular preço total \n[2]Calcular troco \n[3]Relatório de vendas \n[4]Pesquisar compra por dia \n[5]Relatorio de pessoas \n[6]Saída");
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

     if (op==5){
    System.out.println("\n[1]Mostrar clientes \n[2] Mostrar vendedores [3]Mostrar informações da loja");
    op = scanner.nextInt();

    if (op == 1) {
        System.out.println("\nClientes (" + myPlant.contarClientes() + "):");
        for (int i = 0; i < myPlant.clientes.size(); i++) {
            myPlant.clientes.get(i).apresentarse();
            System.out.println();
        }
    } 
    else if (op == 2) {
        System.out.println("\nVendedores (" + myPlant.contarVendedores() + "):");
        for (int i = 0; i < myPlant.vendedores.size(); i++) {
            myPlant.vendedores.get(i).apresentarse();
            System.out.println("Média salarial: R$" + myPlant.vendedores.get(i).calcularMedia());
            System.out.println("Bônus: R$" + myPlant.vendedores.get(i).calcularBonus());
            System.out.println();
        }
    }
    else if (op == 3) {
        System.out.println("\nInformações da Loja:");
        myPlant.apresentarse();
    }
}
}
scanner.close();
}
}