# Paradigmas de Programação: Imperativo e Declarativo

## Introdução
Os paradigmas de programação definem diferentes abordagens para a construção de algoritmos e soluções computacionais. Dois dos principais paradigmas são o **imperativo** e o **declarativo**. Ambos possuem características distintas que impactam a forma como o código é escrito e interpretado.

## Paradigma Imperativo
O paradigma imperativo é baseado na descrição passo a passo das instruções que o computador deve seguir para alcançar um objetivo. Ele se assemelha a um conjunto de comandos organizados sequencialmente, onde o programador especifica **como** a tarefa deve ser realizada. Linguagens como **Java, C e Python** frequentemente utilizam esse paradigma.

### Exemplo em Java
```java
public class Fatorial {
    public static int calcularFatorial(int n) {
        int resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static void main(String[] args) {
        int numero = 5;
        System.out.println("Fatorial de " + numero + " é " + calcularFatorial(numero));
    }
}
```

### Análise
O código acima implementa o cálculo do fatorial de um número de forma iterativa. A função `calcularFatorial` mantém uma variável `resultado`, que é modificada a cada iteração do loop `for`. O fluxo de execução é controlado de maneira explícita, evidenciando a característica do paradigma imperativo.

## Paradigma Declarativo
No paradigma declarativo, a ênfase está em **o que** deve ser resolvido, em vez de **como** resolvê-lo. O programador especifica as regras e relações entre os dados, e o sistema de inferência da linguagem lida com a execução. Exemplos de linguagens declarativas incluem **Prolog, SQL e Haskell**.

### Exemplo em Prolog
```prolog
fatorial(0, 1).
fatorial(N, Resultado) :-
    N > 0,
    N1 is N - 1,
    fatorial(N1, R1),
    Resultado is N * R1.

?- fatorial(5, X).
```

### Análise
Neste código, definimos o cálculo do fatorial utilizando **recursão**. A base da recursão (`fatorial(0, 1).`) define o caso em que o fatorial de 0 é 1. Para valores maiores que 0, o programa usa recursão para calcular o fatorial do número anterior e depois multiplica pelo número atual. Diferente da abordagem imperativa, aqui não há controle explícito de fluxo; o motor de inferência do Prolog determina a sequência de execução automaticamente.

## Comparação
| Característica  | Java (Imperativo) | Prolog (Declarativo) |
|---------------|----------------|----------------|
| Controle de Fluxo | Explícito (uso de loops e variáveis) | Implícito (recursão e regras) |
| Como Funciona | Passo a passo, modificando estado | Baseado em regras e relações |
| Facilidade de Leitura | Pode ser mais verboso, mas direto | Expressivo, mas requer conhecimento de lógica |
| Aplicação Comum | Desenvolvimento de software tradicional | Inteligência artificial, bancos de dados |

## Conclusão
Os paradigmas imperativo e declarativo representam abordagens distintas para a programação. Enquanto o imperativo enfatiza o controle detalhado do fluxo de execução, o declarativo foca na especificação lógica do problema. A escolha entre eles depende da natureza do problema a ser resolvido e da linguagem mais adequada ao contexto.

