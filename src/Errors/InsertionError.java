package Errors;

public class InsertionError extends RuntimeException {
    public InsertionError(String message) {
        super(message);
    }
}
