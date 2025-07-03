public class WeatherData {
    private String cidade;
    private String dataHora;
    private double temperaturaAtual;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private double umidade;
    private String condicaoTempo;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;

    public WeatherData() {
    }

    public WeatherData(String cidade, String dataHora, double temperaturaAtual,
            double temperaturaMaxima, double temperaturaMinima,
            double umidade, String condicaoTempo, double precipitacao,
            double velocidadeVento, String direcaoVento) {
        this.cidade = cidade;
        this.dataHora = dataHora;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.umidade = umidade;
        this.condicaoTempo = condicaoTempo;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public String getCondicaoTempo() {
        return condicaoTempo;
    }

    public void setCondicaoTempo(String condicaoTempo) {
        this.condicaoTempo = condicaoTempo;
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

    /**
     * Converte direção do vento em graus para direção textual
     * 
     * @param graus direção em graus (0-360)
     * @return direção textual (N, NE, E, SE, S, SO, O, NO)
     */
    public static String converterDirecaoVento(double graus) {
        if (graus >= 337.5 || graus < 22.5)
            return "N";
        else if (graus >= 22.5 && graus < 67.5)
            return "NE";
        else if (graus >= 67.5 && graus < 112.5)
            return "E";
        else if (graus >= 112.5 && graus < 157.5)
            return "SE";
        else if (graus >= 157.5 && graus < 202.5)
            return "S";
        else if (graus >= 202.5 && graus < 247.5)
            return "SO";
        else if (graus >= 247.5 && graus < 292.5)
            return "O";
        else
            return "NO";
    }
    /**
     * Traduz as condições climáticas do inglês para o português
     * 
     * @param condicaoIngles condição em inglês da API
     * @return condição traduzida para português
     */
    public static String traduzirCondicao(String condicaoIngles) {
        if (condicaoIngles == null || condicaoIngles.equals("N/A")) {
            return "Não disponível";
        }

        String condicao = condicaoIngles.toLowerCase();

        // Condições básicas
        if (condicao.contains("clear"))
            return "Céu limpo";
        if (condicao.contains("sunny"))
            return "Ensolarado";
        if (condicao.contains("partly cloudy") || condicao.contains("partially cloudy"))
            return "Parcialmente nublado";
        if (condicao.contains("mostly cloudy"))
            return "Muito nublado";
        if (condicao.contains("overcast") || condicao.contains("cloudy"))
            return "Nublado";

        // Chuva
        if (condicao.contains("light rain"))
            return "Chuva fraca";
        if (condicao.contains("moderate rain"))
            return "Chuva moderada";
        if (condicao.contains("heavy rain"))
            return "Chuva forte";
        if (condicao.contains("drizzle"))
            return "Garoa";
        if (condicao.contains("showers"))
            return "Pancadas de chuva";
        if (condicao.contains("rain"))
            return "Chuva";

        // Tempestade
        if (condicao.contains("thunderstorm"))
            return "Tempestade";
        if (condicao.contains("storm"))
            return "Tempestade";

        // Neve
        if (condicao.contains("light snow"))
            return "Neve fraca";
        if (condicao.contains("heavy snow"))
            return "Neve forte";
        if (condicao.contains("snow"))
            return "Neve";
        if (condicao.contains("blizzard"))
            return "Nevasca";

        // Nevoeiro/Neblina
        if (condicao.contains("fog") || condicao.contains("foggy"))
            return "Nevoeiro";
        if (condicao.contains("mist") || condicao.contains("misty"))
            return "Neblina";
        if (condicao.contains("haze"))
            return "Névoa seca";

        // Vento
        if (condicao.contains("windy"))
            return "Ventoso";

        // Outras condições
        if (condicao.contains("dust"))
            return "Poeira";
        if (condicao.contains("smoke"))
            return "Fumaça";
        if (condicao.contains("hail"))
            return "Granizo";
        if (condicao.contains("freezing"))
            return "Congelante";

        // Se não encontrar tradução, retorna o original
        return condicaoIngles;
    }

    /**
     * Valida se os dados essenciais estão presentes
     * 
     * @return true se os dados mínimos estão válidos
     */
    public boolean isValido() {
        return cidade != null && !cidade.trim().isEmpty() &&
                dataHora != null && !dataHora.trim().isEmpty();
    }

    @Override
    public String toString() {
        return String.format(
                "WeatherData{cidade='%s', dataHora='%s', tempAtual=%.1f°C, " +
                        "tempMax=%.1f°C, tempMin=%.1f°C, umidade=%.1f%%, " +
                        "condicao='%s', precipitacao=%.1fmm, vento=%.1f km/h %s}",
                cidade, dataHora, temperaturaAtual, temperaturaMaxima,
                temperaturaMinima, umidade, condicaoTempo, precipitacao,
                velocidadeVento, direcaoVento);
    }
}