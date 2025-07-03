package climaAPI;

public class Clima {

    public double temperaturaAtual;
    public double temperaturaMin;
    public double temperaturaMax;
    public double umidade;
    public String condicao;
    public double precipitacao;
    public double velocidadeVento;
    public String direcaoVento;

    public Clima(double temperaturaAtual, double temperaturaMin, double temperaturaMax, double umidade, String condicao, double precipitacao, double velocidadeVento, String direcaoVento) {
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMin = temperaturaMin;
        this.temperaturaMax = temperaturaMax;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public double getTemperaturaMin() {
        return temperaturaMin;
    }

    public void setTemperaturaMin(double temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    public double getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(double temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public double getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(double precipitacao) {
        this.precipitacao = precipitacao;
    }

    public double getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(double velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public String getDirecaoVento() {
        return direcaoVento;
    }

    public void setDirecaoVento(String direcaoVento) {
        this.direcaoVento = direcaoVento;
    }

    public String traduzirCondicao() {
        if (condicao == null) {
            return "Condição desconhecida";
        }

        String condicaoLower = condicao.toLowerCase();

        if (condicaoLower.contains("clear")) {
            return "Céu limpo";
        } else if (condicaoLower.contains("partly") || condicaoLower.contains("partially")) {
            return "Parcialmente nublado";
        } else if (condicaoLower.contains("overcast")) {
            return "Nublado";
        } else if (condicaoLower.contains("rain")) {
            if (condicaoLower.contains("heavy")) {
                return "Chuva forte";
            } else if (condicaoLower.contains("light")) {
                return "Chuva leve";
            } else {
                return "Chuva";
            }
        } else if (condicaoLower.contains("snow")) {
            return "Neve";
        } else if (condicaoLower.contains("fog")) {
            return "Névoa";
        }

        return condicao;
    }

    @Override
    public String toString() {
        return "\n=== Dados do Clima ===" +
                "\nTemperatura Atual: " + temperaturaAtual + "°C" +
                "\nTemperatura Mínima: " + temperaturaMin + "°C" +
                "\nTemperatura Máxima: " + temperaturaMax + "°C" +
                "\nUmidade: " + umidade + "%" +
                "\nCondição do Tempo: " + traduzirCondicao() +
                "\nPrecipitação: " + precipitacao + " mm" +
                "\nVelocidade do Vento: " + velocidadeVento + " km/h" +
                "\nDireção do Vento: " + direcaoVento;
    }

}
