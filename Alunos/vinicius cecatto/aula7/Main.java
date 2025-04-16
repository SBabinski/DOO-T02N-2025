package aula7;

import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        ProcessaPedido processador = new ProcessaPedido();
        Pedido pedido = null;

        while (opcao != 4) {
            System.out.println("\nMENU");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Exibir Pedido");
            System.out.println("3. Exibir Descrição da Venda");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Cliente: ");
                    String nomeCliente = scanner.nextLine();
                    System.out.print("Idade do Cliente: ");
                    int idadeCliente = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Estado do Endereço: ");
                    String estadoEndereco = scanner.nextLine();
                    System.out.print("Cidade do Endereço: ");
                    String cidadeEndereco = scanner.nextLine();
                    System.out.print("Bairro do Endereço: ");
                    String bairroEndereco = scanner.nextLine();
                    System.out.print("Rua do Endereço: ");
                    String ruaEndereco = scanner.nextLine();
                    System.out.print("Número do Endereço: ");
                    int numeroEndereco = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Detalhes do Endereço: ");
                    String detalhesEndereco = scanner.nextLine();

                    Endereco enderecoCliente = new Endereco(estadoEndereco, cidadeEndereco, bairroEndereco, ruaEndereco, numeroEndereco, detalhesEndereco);
                    Cliente cliente = new Cliente(nomeCliente, idadeCliente, enderecoCliente);

                    System.out.print("Nome do Vendedor: ");
                    String nomeVendedor = scanner.nextLine();
                    System.out.print("Idade do Vendedor: ");
                    int idadeVendedor = scanner.nextInt();
                    scanner.nextLine();

                    Endereco enderecoLoja = new Endereco("PR", "Laranjeiras Do Sul", "Centro", "Rua das Flores", 123, "Loja 1");
                    Loja loja = new Loja("Floricultura Linda", "Floricultura LTDA", "12345678000199", enderecoLoja);

                    double salarioBase = 2000.0;
                    ArrayList<Double> salarioRecebido = new ArrayList<>(Arrays.asList(2000.0, 2100.0, 2200.0));
                    Vendedor vendedor = new Vendedor(nomeVendedor, idadeVendedor, loja.getNomeFantasia(), enderecoLoja, salarioBase, salarioRecebido);

                    List<Item> itens = new ArrayList<>();
                    boolean adicionarMaisItens = true;

                    while (adicionarMaisItens) {
                        System.out.print("ID do Item: ");
                        int idItem = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nome do Item: ");
                        String nomeItem = scanner.nextLine();
                        System.out.print("Categoria do Item: ");
                        String categoriaItem = scanner.nextLine();
                        System.out.print("Preço do Item: ");
                        double precoItem = scanner.nextDouble();
                        scanner.nextLine();

                        Item item = new Item(idItem, nomeItem, categoriaItem, precoItem);
                        itens.add(item);

                        System.out.print("Deseja adicionar mais itens? (sim/não ou 1/2): ");
                        String resposta = scanner.nextLine();
                        if (resposta.equalsIgnoreCase("não") || resposta.equals("2")) {
                            adicionarMaisItens = false;
                        }
                        else if (resposta.equalsIgnoreCase("sim") || resposta.equals("1")) {
                        }
                    }

                    pedido = processador.processar(
                            101,
                            LocalDate.now(),
                            LocalDate.now(),
                            LocalDate.now().plusDays(2),
                            cliente,
                            vendedor,
                            loja,
                            itens
                    );
                    System.out.println("Pedido criado com sucesso!");
                    break;

                case 2:
                    if (pedido != null) {
                        System.out.println("ID do Pedido: " + pedido.getId());
                        System.out.println("Data do Pedido: " + pedido.getDataCriacao());
                        System.out.println("Cliente: " + pedido.getCliente().nome);
                        System.out.println("Vendedor: " + pedido.getVendedor().getNome());
                    } else {
                        System.out.println("Nenhum pedido foi criado ainda.");
                    }
                    break;

                case 3:
                    if (pedido != null) {
                        System.out.println(pedido.gerarDescricaoVenda());
                    } else {
                        System.out.println("Nenhum pedido foi criado ainda.");
                    }
                    break;

                case 4:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}
