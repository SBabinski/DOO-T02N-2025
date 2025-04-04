import domain.*;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        var lojaAmericanas = new Loja("Rua das Américas", "Liberdade", "São Paulo", "12341234123412", "Americanas LTDA", "Americanas");
        var enderecoVaumir = new Endereco("Paraná", "Cascavel", "Periolo", 120, "Casa");
        var vendedorVaumir = new Vendedor("Vaumir", 43, lojaAmericanas, enderecoVaumir, 2200.00);

        lojaAmericanas.apresentarse();
        vendedorVaumir.apresentarse();
        vendedorVaumir.getEndereco().apresentarLogradouro();
        vendedorVaumir.calcularBonus();

        vendedorVaumir.getSalarioRecebido().add(2100.00);
        vendedorVaumir.getSalarioRecebido().add(1900.32);
        vendedorVaumir.getSalarioRecebido().add(30000.00);
        vendedorVaumir.calcularMedia();

        var clienteRafael = new Cliente("Rafael", 22, enderecoVaumir);
        clienteRafael.apresentarse();
        var clientePedroUsaDrogras = new Cliente("Pedro", 22, enderecoVaumir);
        clientePedroUsaDrogras.apresentarse();

        lojaAmericanas.getClientes().add(clienteRafael);
        lojaAmericanas.getClientes().add(clientePedroUsaDrogras);
        lojaAmericanas.contarClientes();
        lojaAmericanas.contarVendedores();

        var item1 = new Item(1, "Vaso de Planta", "Decoração", 49.90);
        var item2 = new Item(2, "Terra Vegetal", "Jardinagem", 15.00);
        var item3 = new Item(3, "Adubo Orgânico", "Fertilizante", 25.50);

        var itensPedido = new ArrayList<Item>();
        itensPedido.add(item1);
        itensPedido.add(item2);
        itensPedido.add(item3);

        ProcessaPedido processaPedido = new ProcessaPedido();
        Pedido pedido = processaPedido.processar(
                1,
                clienteRafael,
                vendedorVaumir,
                lojaAmericanas,
                itensPedido,
                new Date()
        );

        pedido.gerarDescricaoVenda();
    }
}
