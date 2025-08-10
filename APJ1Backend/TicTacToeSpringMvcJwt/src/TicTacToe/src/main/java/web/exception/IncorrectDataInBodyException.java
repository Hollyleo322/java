package web.exception;

public class IncorrectDataInBodyException extends RuntimeException{
    public IncorrectDataInBodyException()
    {
        super("User must send 10 in the body of request");
    }
}
