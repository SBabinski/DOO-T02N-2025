package Alunos.FelipeHeringer.Aula09;

public class Calculadora {

    public Calculadora(){

    }

    protected double somarNumeros(double num1, double num2){
        System.out.println("Realizando a soma dos numeros: " + num1 + " " + num2);

        return num1 + num2;

    }

    protected double subtrairNumeros(double num1, double num2){
        System.out.println("Realizando a subtração dos numeros: " + num1 + " " + num2);

        return num1 - num2;
    }

    protected double multiplicarNumeros(double num1, double num2){
        System.out.println("Realizando a multiplicação dos numeros: " + num1 + " " + num2);

        return num1 * num2;

    }

    protected double dividirNumeros(double num1, double num2) {
        System.out.println("Realizando a divisão dos numeros: " + num1 + " " + num2);

        if(num2 == 0){
            throw new ArithmeticException("Não é possivel realizar uma divisão por zero");
        }

        return num1 / num2;
    }
    
}
