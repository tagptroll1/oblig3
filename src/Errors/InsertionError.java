package Errors;

public class InsertionError extends RuntimeException {
    /**
     * Custom error message for when adding objects to db fails
     * @param message
     */
    public InsertionError(String message) {
        super(message);
    }
}
