package dook.exception;

public class EmptyTaskListException extends DookException {
    public EmptyTaskListException() {
        super("You are alone in this darkness.");
    }
}
