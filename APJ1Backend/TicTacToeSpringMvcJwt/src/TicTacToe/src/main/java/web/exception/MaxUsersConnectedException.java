package web.exception;

public class MaxUsersConnectedException extends RuntimeException {
    public MaxUsersConnectedException() {
        super("Max of players already are connected");
    }
}
