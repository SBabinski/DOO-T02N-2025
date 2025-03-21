import java.time.LocalDate;
import java.util.*;

public class RegistroVenda {
    // Mapa de vendas por dia, para um mês específico
    private Map<LocalDate, List<Venda>> vendasPorDia = new HashMap<>();

    public void adicionarVenda(Venda venda) {
        vendasPorDia.computeIfAbsent(venda.getDataVenda(), k -> new ArrayList<>()).add(venda);
    }

    public List<Venda> getVendas() {
        // Retorna todas as vendas registradas
        List<Venda> todasVendas = new ArrayList<>();
        for (List<Venda> vendas : vendasPorDia.values()) {
            todasVendas.addAll(vendas);
        }
        return todasVendas;
    }

    public void buscarVendasPorDia(int dia, int mes, int ano) {
        LocalDate data = LocalDate.of(ano, mes, dia);
        List<Venda> vendas = vendasPorDia.get(data);
        
        if (vendas != null) {
            System.out.println("Vendas no dia " + data + ":");
            for (Venda v : vendas) {
                v.exibirVenda();
            }
        } else {
            System.out.println("Nenhuma venda encontrada para o dia " + data);
        }
    }

    public void buscarVendasPorMes(int mes, int ano) {
        System.out.println("Vendas no mês " + mes + "/" + ano + ":");
        for (Map.Entry<LocalDate, List<Venda>> entry : vendasPorDia.entrySet()) {
            if (entry.getKey().getMonthValue() == mes && entry.getKey().getYear() == ano) {
                for (Venda v : entry.getValue()) {
                    v.exibirVenda();
                }
            }
        }
    }
}
