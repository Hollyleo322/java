package web.exception;

public class UiidExistsException extends RuntimeException{
    public UiidExistsException(int uiid)
    {
        super(uiid + " is existing, error");
    }
}
