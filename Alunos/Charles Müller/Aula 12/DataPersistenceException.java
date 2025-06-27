/**
 * Exceção específica para erros relacionados à persistência de dados
 *
 * @author Charles Müller
 * @version 1.0
 */
public class DataPersistenceException extends Exception {

    /**
     * Construtor com mensagem
     */
    public DataPersistenceException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa
     */
    public DataPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor apenas com causa
     */
    public DataPersistenceException(Throwable cause) {
        super(cause);
    }
}
