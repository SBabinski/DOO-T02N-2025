package aula7;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ProcessaPedido {

    public Pedido processar(int id, LocalDate localDate, LocalDate localDate2, LocalDate localDate3,
                            Cliente cliente, Vendedor vendedor, Loja lojaVendedor, List<Item> itens) {

        Pedido pedido = new Pedido(id, localDate, localDate2, localDate3, cliente, vendedor, lojaVendedor, itens);

        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado!");
        } else {
            System.out.println("Pagamento não confirmado. Reserva vencida.");
        }

        return pedido;
    }
    private boolean confirmarPagamento(Pedido pedido) {
        LocalDate vencimento = pedido.getDataVencimentoReserva();
        Date dataVencimentoDate = Date.from(vencimento.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date hoje = new Date();

        return !hoje.after(dataVencimentoDate);
    }
}
