package dook.exception;

public class UnknownCommandException extends DookException {
    public UnknownCommandException() {
        super("You are not supposed to be here!");
    }
}
