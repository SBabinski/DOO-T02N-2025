package Alunos.FelipeHeringer.Aula04;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RegistroVendas {
    private HashMap<String,ArrayList<ArrayList<Venda>>> vendasDiarias;
    private Integer numVenda ;
    
    
    public RegistroVendas(){
        this.vendasDiarias = new HashMap<>();
        this.numVenda = 0;
    }

    public void registrarVendas(Integer qtdPlantas, Double valorVenda, Double descontosAplicados) {
        // Dia Atual 
        LocalDate.now(
            ZoneId.of("America/Sao_Paulo")
        )
        .format(
            DateTimeFormatter.ofPattern("dd.MM.uuuu")
        );

        String diaAtual = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));

        this.numVenda +=1;
    
        // Pegar a Lista Existente ou criar uma
        vendasDiarias.putIfAbsent(diaAtual, new ArrayList<>());

        ArrayList<Venda> registrosVendas = new ArrayList<>();
        registrosVendas.add(new Venda(numVenda, qtdPlantas, valorVenda, descontosAplicados));
        
        vendasDiarias.get(diaAtual).add(registrosVendas);

    }
    public void  visualizarRegistrosVendas() {
        vendasDiarias.forEach((key, value) -> {
            System.out.println("Vendas no Dia " + key + " Quantidade Totais de Vendas " + value.size());
            value.stream()
                .flatMap(List:: stream)
                .forEach(System.out::println);
        });
    }

    public void buscarQtdVendasDia(String data) {
        Integer numVendasDia = vendasDiarias.getOrDefault(data,new ArrayList<>()).size();
        System.out.println("Numero de venda no Dia " + data + " é : " + numVendasDia);
        
    }

    
}

