public class Vendedor {
    private String nomeVendedor;
    private int idadeVendedor;
    private String lojaVendedor;
    private String cidadeVendedor;
    private String bairroVendedor;
    private String ruaVendedor;
    private double salarioBaseVendedor;
    private double[] salarioRecebidoVendedor;

    public Vendedor(String nomeVendedor, int idadeVendedor, String lojaVendedor, String cidadeVendedor, String bairroVendedor, String ruaVendedor, double salarioBaseVendedor, double[] salarioRecebidoVendedor) {
        this.nomeVendedor = nomeVendedor;
        this.idadeVendedor = idadeVendedor;
        this.lojaVendedor = lojaVendedor;
        this.cidadeVendedor = cidadeVendedor;
        this.bairroVendedor = bairroVendedor;
        this.ruaVendedor = ruaVendedor;
        this.salarioBaseVendedor = salarioBaseVendedor;
        this.salarioRecebidoVendedor = salarioRecebidoVendedor;
    }

    public void apresentarse() {
        System.out.println("Nome do Vendedor: " + nomeVendedor);
        System.out.println("Idade do Vendedor: " + idadeVendedor);
        System.out.println("Loja do Vendedor: " + lojaVendedor);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebidoVendedor) {
            soma += salario;
        }
        return soma / salarioRecebidoVendedor.length;
    }

    public double calcularBonus() {
        return salarioBaseVendedor * 0.2;
    }
}
