public class Carro extends Veiculo {
    public Carro(String modelo, String marca, String ano) {
        super(modelo, marca, ano);
    }

    public Carro() {
    }

    @Override
    public void trocarPeca() {
        System.out.println("Trocando a peça do CARRO...");
        System.out.println("Troca concluída!");
    }
}
