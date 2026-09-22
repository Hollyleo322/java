package web.exception;

public class ChangedPreviousMovesException extends RuntimeException{
    public ChangedPreviousMovesException()
    {
        super("Changed previous moves");
    }
}
