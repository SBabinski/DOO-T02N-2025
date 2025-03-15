import java.util.ArrayList;
import java.util.HashMap;


public class RegistroVendas {
    private HashMap<Integer,ArrayList<Double>> registroVendas; 
    private Integer count ;

    public RegistroVendas(){
        this.registroVendas = new HashMap<>();
        this.count = 0;
    }

    public void adicionarRegistro(Integer qtdPlantas, Double valorVenda, Double descontosAplicados){
        this.count +=1;
        ArrayList<Double> registro = new ArrayList<>();
        Double quantidadePlantas = Double.valueOf(qtdPlantas);
        registro.add(quantidadePlantas);
        registro.add(valorVenda);
        registro.add(descontosAplicados);
        this.registroVendas.put(count,registro);
    }
    public void  visualizarRegistrosVendas() {
        registroVendas.entrySet().stream()
                .forEach(entry -> System.out.println("Venda No: " + entry.getKey() + "->"
                        + " Quntidade de Plantas = " + entry.getValue().get(0)
                        + " Valor da Venda = " +  entry.getValue().get(1)
                        + " Desconto Aplicado = " + entry.getValue().get(2)
                ));
    }

    
}

