package web.exception;

public class WaitingForSecondPlayerException extends RuntimeException {
    public WaitingForSecondPlayerException() {
        super("Waiting for second player");
    }
}
