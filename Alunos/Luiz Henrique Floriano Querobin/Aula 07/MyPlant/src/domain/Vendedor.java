package domain;

import java.util.ArrayList;

public class Vendedor extends Empregado {

    public Vendedor(String nome, Integer idade, Loja loja, Endereco endereco, Double salarioBase) {
        super(nome, idade, loja, endereco, salarioBase);
    }
}
