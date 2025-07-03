package Aula12;

public class Days {
    double temp;
    double tempmin;
    double tempmax;
    double humidity;
    String conditions;
    double precip;
    double windspeed;
    double winddir;

    @Override
    public String toString() {
        return  "\n Teperatura " + temp +
                "\n Temp Min " + tempmin +
                "\n Temperatura Max " + tempmax +
                "\n Humidade " + humidity +
                "\n Condição " + conditions +
                "\n Precipitação " + precip +
                "\n Velocidade Vento " + windspeed +
                "\n Direção Vento " + winddir;
    }
}
