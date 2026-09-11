package dook.exception;

public class EmptyCommandLogException extends DookException {
    public EmptyCommandLogException() {
        super("There is no past for you to fix. History starts here.");
    }
}
