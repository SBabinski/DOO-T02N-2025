import java.util.Date;
import java.util.List;

public class ProcessaPedido {
    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja, List<Item> itens) {
        Date dataCriacao = new Date(); // Data atual

        // Define data de vencimento para 7 dias no futuro
        Date dataVencimentoReserva = new Date();
        dataVencimentoReserva.setTime(dataCriacao.getTime() + 7 * 24 * 60 * 60 * 1000);

        Pedido pedido = new Pedido(id, dataCriacao, dataVencimentoReserva, cliente, vendedor, loja);

        // Adiciona os itens ao pedido
        for (Item item : itens) {
            pedido.adicionarItem(item);
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date dataAtual = new Date();
        if (dataAtual.after(pedido.getDataVencimentoReserva())) {
            System.out.println("Reserva vencida. Não é possível confirmar o pagamento.");
            return false;
        }

        pedido.setDataPagamento(dataAtual);
        System.out.println("Pagamento confirmado com sucesso!");
        return true;
    }

    // Método para testar a confirmação de pagamento
    public boolean testarConfirmacaoPagamento(Pedido pedido) {
        return confirmarPagamento(pedido);
    }
}