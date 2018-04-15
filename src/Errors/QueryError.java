package Errors;

public class QueryError extends RuntimeException {
    public QueryError(String error){
        super(error);
    }
}
