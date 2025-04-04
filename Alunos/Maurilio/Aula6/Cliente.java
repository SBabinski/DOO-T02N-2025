public class Cliente {
    private String nomeCliente;
    private int idadeCliente;
    private String cidadeCliente;
    private String bairroCliente;
    private String ruaCliente;

    public Cliente(String nomeCliente, int idadeCliente, String cidadeCliente, String bairroCliente, String ruaCliente) {
        this.nomeCliente = nomeCliente;
        this.idadeCliente = idadeCliente;
        this.cidadeCliente = cidadeCliente;
        this.bairroCliente = bairroCliente;
        this.ruaCliente = ruaCliente;
    }

    public void apresentarse() {
        System.out.println("Nome do Cliente: " + nomeCliente);
        System.out.println("Idade do Cliente: " + idadeCliente);
    }
}
