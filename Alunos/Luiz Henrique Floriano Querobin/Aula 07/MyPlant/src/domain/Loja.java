package domain;

import java.util.ArrayList;

public class Loja {

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private ArrayList<Vendedor> vendedors;
    private ArrayList<Cliente> clientes;

    public void contarClientes() {
        System.out.println("A loja tem " + clientes.size() + " clientes.");
    }

    public void contarVendedores() {
        System.out.println("A loja tem " + vendedors.size() + " vendedores.");
    }

    public void apresentarse() {
        System.out.printf("Nome Fantasia: %s - CNPJ: %s - Endereço: %s %s %s\n", nomeFantasia, cnpj, cidade, bairro, rua);
    }

    public Loja() {
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public ArrayList<Vendedor> getVendedors() {
        return vendedors;
    }

    public void setVendedors(ArrayList<Vendedor> vendedors) {
        this.vendedors = vendedors;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Loja(String rua, String bairro, String cidade, String cnpj, String razaoSocial, String nomeFantasia) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.vendedors = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Loja{" +
                "nomeFantasia='" + nomeFantasia + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", vendedors=" + vendedors +
                ", clientes=" + clientes +
                '}';
    }
}
