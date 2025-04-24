import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<Veiculo> listaVeiculos = new ArrayList<>();
    public static ArrayList<Veiculo> listaVeiculosServico = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Bem - Vindo!");

        boolean sair = true;

        while (sair) {
            System.out.println("Selecione a opção que deseja executar:");
            System.out.println("""
                    1. Cadastrar cliente;
                    2. Cadastrar veículo;
                    3. Buscar veículo por modelo;
                    4. Buscar veículo por marca;
                    5. Registrar entrada de um veículo para serviço;
                    6. Registrar saída de um veículo após serviço;
                    7. Verificar disponibilidade de mecânicos;
                    8. Realizar a troca de óleo do veículo;
                    9. Realizar troca de uma peça;
                    10. Sair;
                    """);

            int escolha = scan.nextInt();
            scan.nextLine();

            if (escolha == 1) {
                cadastrarCliente();
            }

            if (escolha == 2) {
                cadastrarVeiculo();
            }

            if (escolha == 3) {
                buscarVeiculosModelo();
            }

            if (escolha == 4) {
                buscarVeiculosMarca();
            }

            if (escolha == 5) {
                registrarEntrada();
            }

            if (escolha == 6) {
                registrarSaida();
            }

            if (escolha == 7) {
                verificarMecanicos();
            }

            if (escolha == 8) {
                trocarOleo();
            }

            if (escolha == 9) {
                trocarPeca();
            }

            if (escolha == 10) {
                sair = false;
            }
        }
    }

    public static void cadastrarCliente() {
        Clientes clientes = new Clientes("Bruno", 22, "Estudante", listaVeiculos);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void cadastrarVeiculo() {
        Moto moto = new Moto("cg", "honda", "2016");
        Carro carro = new Carro("fiesta", "ford", "2017");

        listaVeiculos.add(moto);
        listaVeiculos.add(carro);

        System.out.println("Veículos cadastrados com sucesso!");
    }

    public static void buscarVeiculosModelo() {
        System.out.println("Entre com o modelo do veículo que deseja buscar: ");

        String modelo = scan.nextLine();

        for (Veiculo veiculo : listaVeiculos) {
            if (veiculo.getModelo().equalsIgnoreCase(modelo)) {
                System.out.println("Veículo encontrado!");
                System.out.println("Modelo: " + veiculo.getModelo());
                System.out.println("Marca: " + veiculo.getMarca());
                System.out.println("Ano: " + veiculo.getAno());
            }
        }
    }

    public static void buscarVeiculosMarca() {
        System.out.println("Entre com a marca do veículo que deseja buscar: ");
        String marca = scan.nextLine();

        for (Veiculo veiculo : listaVeiculos) {
            if (veiculo.getMarca().equalsIgnoreCase(marca)) {
                System.out.println("Veículo encontrado!");
                System.out.println("Modelo: " + veiculo.getModelo());
                System.out.println("Marca: " + veiculo.getMarca());
                System.out.println("Ano: " + veiculo.getAno());
            }
        }
    }

    public static void registrarEntrada() {
        Carro carro1 = new Carro("civic", "honda", "2006");

        listaVeiculosServico.add(carro1);

        System.out.println("Entrada registrada!");
    }

    public static void registrarSaida() {
        listaVeiculosServico.remove(0);

        System.out.println("Saída registrada!");
    }

    public static void verificarMecanicos() {
        // Apenas um tapa buraco simples...

        System.out.println("Quantos profissionais serão necessários para o serviço?");
        System.out.println("Digite a quantidade: ");

        int quant = scan.nextInt();

        if (quant == 1) {
            System.out.println("Temos mecânicos disponíveis!");
        }

        else {
            System.out.println("Não temos profissionais no momento, volte mais tarde...");
        }
    }

    public static void trocarOleo() {
        Carro carrovelho = new Carro("uno", "fiat", "2010");
        carrovelho.trocarOleo();
    }

    public static void trocarPeca() {
        Moto motovelha = new Moto("titan", "honda", "2018");
        motovelha.trocarPeca();
    }
}
