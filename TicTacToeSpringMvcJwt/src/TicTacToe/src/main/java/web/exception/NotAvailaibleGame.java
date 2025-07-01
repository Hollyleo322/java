package web.exception;

public class NotAvailaibleGame extends RuntimeException {
    public NotAvailaibleGame() {
        super("This game is not available for you");
    }
}
