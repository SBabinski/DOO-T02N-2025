public class Veiculo {
    private String modelo;
    private String marca;
    private String ano;

    public Veiculo(String modelo, String marca, String ano) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
    }

    public Veiculo() {
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void trocarOleo() {
        System.out.println("Realizando a troca de óleo do veículo...");
        System.out.println("Troca realizada com sucesso!");
    }

    public void trocarPeca() {
        System.out.println("Realizando troca das peças...");
    }
}
