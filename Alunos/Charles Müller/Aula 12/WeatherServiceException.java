/**
 * Exceção específica para erros relacionados ao serviço meteorológico
 *
 * @author Charles Müller
 * @version 1.0
 */
public class WeatherServiceException extends Exception {

    /**
     * Construtor com mensagem
     */
    public WeatherServiceException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa
     */
    public WeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor apenas com causa
     */
    public WeatherServiceException(Throwable cause) {
        super(cause);
    }
}
