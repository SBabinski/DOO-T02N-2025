import domain.Cliente;
import domain.Loja;
import domain.Vendedor;

public class Main {
    public static void main(String[] args) {
        var lojaAmericanas = new Loja("Rua das Américas", "Liberdade", "São Paulo", "12341234123412", "Americanas LTDA", "Americanas");
        var vendedorVaumir = new Vendedor("Vaumir", 43, lojaAmericanas, "Cascavel", "Periolo", "Cleber", 2200.00);
        lojaAmericanas.apresentarse();
        vendedorVaumir.apresentarse();
        vendedorVaumir.calcularBonus();
        vendedorVaumir.getSalarioRecebido().add(2100.00);
        vendedorVaumir.getSalarioRecebido().add(1900.32);
        vendedorVaumir.getSalarioRecebido().add(30000.00);
        vendedorVaumir.calcularMedia();
        var clienteRafael = new Cliente("Rafael", 22, "Cascavel", "Grajau", "12");
        clienteRafael.apresentarse();
        var clientePedroUsaDrogras = new Cliente("Pedro", 22, "Cascavel", "14", "Africa");
        clientePedroUsaDrogras.apresentarse();
        lojaAmericanas.getClientes().add(clienteRafael);
        lojaAmericanas.getClientes().add(clientePedroUsaDrogras);
        lojaAmericanas.contarClientes();
        lojaAmericanas.contarVendedores();

    }
}