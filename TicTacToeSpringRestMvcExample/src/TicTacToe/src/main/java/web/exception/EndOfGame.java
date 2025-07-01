package web.exception;

public class EndOfGame extends RuntimeException{
    public EndOfGame(String what)
    {
        super("End of game " + what);
    }
}
