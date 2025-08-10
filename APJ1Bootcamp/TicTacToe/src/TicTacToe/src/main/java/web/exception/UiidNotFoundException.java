package web.exception;

public class UiidNotFoundException extends RuntimeException{
    public UiidNotFoundException(int uiid)
    {
        super("Could not find uiid " + uiid);
    }
}
