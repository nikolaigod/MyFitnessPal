package exception;

public class UnknownFoodException extends Exception {

    public UnknownFoodException(String message) {
        super(message);
    }

    public UnknownFoodException(String message, Throwable cause) {
        super(message, cause);
    }

}