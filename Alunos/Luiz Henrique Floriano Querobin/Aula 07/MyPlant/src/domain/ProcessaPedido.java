package domain;

import java.util.ArrayList;
import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(
            Integer id,
            Cliente cliente,
            Vendedor vendedor,
            Loja loja,
            ArrayList<Item> itens,
            Date dataPagamento
    ) {
        Pedido pedido = new Pedido(id, dataPagamento, cliente, vendedor, loja, itens);

        if (confirmarPagamento(pedido.getDataVencimentoReserva())) {
            System.out.println("Pagamento confirmado.");
        } else {
            System.out.println("Pagamento não confirmado: reserva vencida.");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Date dataVencimentoReserva) {
        Date dataAtual = new Date();
        return dataAtual.compareTo(dataVencimentoReserva) <= 0;
    }
}
