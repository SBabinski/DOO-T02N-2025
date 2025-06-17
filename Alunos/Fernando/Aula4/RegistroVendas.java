package Aula4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroVendas {

     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

     private int quantidade;
     private double preco;
     private double total;
     private boolean comDesconto;
     private LocalDateTime data;

     public RegistroVendas(int quantidade, double preco, double total, boolean comDesconto, LocalDateTime data) {
          this.quantidade = quantidade;
          this.preco = preco;
          this.total = total;
          this.comDesconto = comDesconto;
          this.data = data;
     }

     public RegistroVendas() {
       
     }

     public int getQuantidade() {
          return quantidade;
     }

     public double getPreco() {
          return preco;
     }

     public double getTotal() {
          return total;
     }

     public boolean getComDesconto() {
          return comDesconto;
     }

     public LocalDateTime getData() {
          return data;
     }

     public void setQuantidade(int quantidade) {
          this.quantidade = quantidade;
     }

     public void setPreco(double preco) {
          this.preco = preco;
     }

     public void setTotal(double total) {
          this.total = total;
     }

     public void setComDesconto(boolean comDesconto) {
          this.comDesconto = comDesconto;
     }

     public void setData(LocalDateTime data) {
          this.data = data;
     }

     @Override
     public String toString() {
          if (comDesconto == true) {
               return "[quantidade: " + quantidade + ", preco: " + preco + ", total: " + total + ", com 5% de desconto, em " + data.format(formatter) + "]";
          } else {
               return "[quantidade: " + quantidade + ", preco: " + preco + ", total: " + total + ", em " + data.format(formatter) + "]";
          }
     }
}