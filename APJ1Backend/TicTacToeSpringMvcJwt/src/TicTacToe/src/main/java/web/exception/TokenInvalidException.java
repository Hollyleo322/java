package web.exception;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Token is invalid, please authorize one more time");
    }
}
