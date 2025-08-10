package web.exception;

public class LoginIsUnavailable extends RuntimeException {
    public LoginIsUnavailable() {
        super("Login is unavailable");
    }
}
