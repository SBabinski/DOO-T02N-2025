package pacote;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Projeto {
	static List<String> vendas = new ArrayList<>();
	public static Scanner scan = new Scanner(System.in);
	static float[][] data = new float[12][31]; 
	static int cont = 1;
	
	public static void main (String []Args) {
		while (true) {
            System.out.println("=== MENU ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Registro de Vendas");
            System.out.println("[4] - Pesquisar Quantidade de Vendas por Data");
            System.out.println("[5] - Sair");
            System.out.println("Escolha uma opção: ");
            
            int opcao = scan.nextInt();
            
            switch (opcao) {
                case 1:
                    calculoPrecoTotal();
                    break;
                case 2:
                    calculoDeTroco();
                    break;
                case 3:
                	System.out.println("Registro de vendas:");
                	if(vendas.isEmpty()) {
                		System.out.println("A lista está vazia.");
                	}
                	else {
                		for (String venda : vendas) {
                            System.out.println(venda);
                        }
                	}
                	break;
                case 4:
                	consultarQuantidadeData();
                    break;
                	
                case 5:
                    System.out.println("Saindo...");
                    scan.close();
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
		
	}
	public static void calculoPrecoTotal() {
        int quantidade;
        float valor;

        while (true) {
            try {
                System.out.println("Digite quantas plantas foram compradas:");
                quantidade = scan.nextInt();
                if (quantidade > 0) {
                    break;
                } else {
                    System.out.println("Erro: A quantidade de plantas deve ser maior que 0.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número inteiro válido.");
                scan.nextLine();
            }
        }

        while (true) {
            try {
                System.out.println("Digite o valor unitário da planta:");
                valor = scan.nextFloat();
                if (valor > 0) {
                    break;
                } else {
                    System.out.println("Erro: O valor da planta deve ser maior que 0.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número decimal válido.");
                scan.nextLine(); 
            }
        }

        float total = quantidade * valor;

        if (quantidade > 10) {
            float desconto = valor * 0.05f;
            valor -= desconto;
            total = quantidade * valor;
            System.out.println("O cliente comprou mais de 10 plantas! Desconto de 5% aplicado.");
        }

        System.out.println("O cálculo do preço total: " + String.format("%.2f", total));
        String a = "Venda " + cont + ": Plantas Compradas: " + quantidade + " Valor Recebido: " + String.format("%.2f", valor);
        vendas.add(a);
        cont++;
        addMatriz(valor);
       
    }
	
	public static void calculoDeTroco () {
		System.out.println("Digite o valor entregue pelo cliente");
		float valorCliente = scan.nextFloat();
		System.out.println("Digite o valor total da compra");
		float valor = scan.nextFloat();
		if (valorCliente < valor) {
			System.out.println("ERRO: O valor entregue pelo cliente não é o suficiente para pagar as flores.");
		}
		else {
			float troco = valorCliente - valor;
			System.out.println("O cliente precisa receber R$ " + String.format("%.2f", troco) + " de troco");
		}
	
		
	}
	
   public static void addMatriz (float valorRecebido) {
	   int dia;
       int mes;
       while (true) {
           try {
               System.out.println("Para registro no sistema, digite o dia da venda:");
               dia = scan.nextInt();
               if (dia > 0 && dia < 32) {
                   break;
               } else {
                   System.out.println("Erro: Digite um dia válido.");
               }
           } catch (Exception e) {
               System.out.println("Entrada inválida! Digite um número inteiro válido.");
               scan.nextLine();
           }
       }
       while (true) {
           try {
               System.out.println("Agora digite o mês (utilizando números de 1 a 12):");
               mes = scan.nextInt();
               if (mes > 0 && mes < 13) {
                   break;
               } else {
                   System.out.println("Erro: Digite um mês válido.");
               }
           } catch (Exception e) {
               System.out.println("Entrada inválida! Digite um número inteiro válido.");
               scan.nextLine();
           }
       }
       dia = dia - 1;
       mes = mes - 1;
       data[mes][dia] += valorRecebido;
   }
   
   public static void consultarQuantidadeData () {
	   int dia;
       int mes;
	   while (true) {
           try {
               System.out.println("Digite o dia que deseja consultar:");
               dia = scan.nextInt();
               if (dia > 0 && dia < 32) {
                   break;
               } else {
                   System.out.println("Erro: Digite um dia válido.");
               }
           } catch (Exception e) {
               System.out.println("Entrada inválida! Digite um número inteiro válido.");
               scan.nextLine();
           }
       }
	   while (true) {
           try {
               System.out.println("Digite o mês que deseja consultar (utilizando números de 1 a 12):");
               mes = scan.nextInt();
               if (mes > 0 && mes < 13) {
                   break;
               } else {
                   System.out.println("Erro: Digite um mês válido.");
               }
           } catch (Exception e) {
               System.out.println("Entrada inválida! Digite um número inteiro válido.");
               scan.nextLine();
           }
       }
	   if (data[mes-1][dia-1] == 0) {
		   System.out.println("Nenhuma venda foi registrada na data informada!");
	   }
	   else {
		   System.out.println("Valor total de vendas no dia " + dia + "/" + mes + " :" );
		   System.out.println(data[mes-1][dia-1]);
	   }
   }
   
}
