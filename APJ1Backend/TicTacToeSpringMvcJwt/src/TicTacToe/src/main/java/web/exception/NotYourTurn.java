package web.exception;

public class NotYourTurn extends RuntimeException {
    public NotYourTurn() {
        super("This is not your turn");
    }
}
