## Resumo: Paradigmas Imperativo e Declarativo

Na programação, paradigmas definem abordagens distintas para solucionar problemas e estruturar o código. Dois paradigmas fundamentais são o **imperativo** e o **declarativo**, cada um com características particulares.

### Paradigma Imperativo

O paradigma imperativo é caracterizado pela descrição detalhada de comandos, determinando passo a passo **como** um objetivo deve ser alcançado. Nesse modelo, o desenvolvedor precisa explicitar cada ação e controle de fluxo do programa.

**Exemplo em Java (Imperativo):**

```java
public boolean verificaPar(int numero) {
    if (numero % 2 == 0) {
        return true;
    } else {
        return false;
    }
}
```

Este trecho em Java verifica se um número é par ou ímpar através do operador módulo (%). A lógica imperativa fica evidente ao passo que cada instrução determina explicitamente o comportamento do programa, detalhando o processo necessário para alcançar o resultado desejado.

### Paradigma Declarativo

Já o paradigma declarativo enfatiza o **que** deve ser feito, deixando a implementação detalhada a cargo do próprio sistema ou compilador. Neste paradigma, o código tende a ser mais próximo à lógica do problema, expressando a solução por meio de regras ou consultas.

**Exemplo em Prolog (Declarativo):**

```prolog
par(X) :- 0 is X mod 2.
```

Nesse exemplo, Prolog declara uma regra que define um número como par se seu resto ao dividir por 2 for zero. O programador não especifica os passos exatos que o computador deve seguir, mas apenas a condição que determina a solução.

### Comparação dos Códigos

O código Java exemplifica o paradigma imperativo ao expor claramente cada etapa necessária para a solução, utilizando condicionais e controle explícito de fluxo. Já o código Prolog demonstra uma abordagem declarativa, onde apenas a lógica da solução é descrita, e os detalhes da implementação são abstraídos, ficando sob responsabilidade do motor lógico da linguagem.

Ambas as abordagens cumprem eficientemente o mesmo objetivo – identificar se um número é par –, porém cada paradigma tem suas vantagens e contextos específicos de utilização. Enquanto o imperativo fornece controle preciso sobre cada etapa, o declarativo oferece clareza e simplicidade ao abstrair detalhes operacionais.

