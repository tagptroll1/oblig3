package Errors;

public class QueryError extends RuntimeException {
    /**
     * Error that throws when a querry returns nothing or fails
     * @param error
     */
    public QueryError(String error){
        super(error);
    }
}
