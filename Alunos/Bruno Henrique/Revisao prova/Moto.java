public class Moto extends Veiculo {
    public Moto(String modelo, String marca, String ano) {
        super(modelo, marca, ano);
    }

    public Moto() {
    }

    @Override
    public void trocarPeca() {
        System.out.println("Trocando a peça da MOTO...");
        System.out.println("Troca concluída!");
    }
}
